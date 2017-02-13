package net.boreeas.reweave

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.reflect.TypeToken
import net.boreeas.reweave.data.*
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import java.util.zip.GZIPInputStream
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


/**
 * An api connection to the shardbound server. This is the most basic connection, permitting only operations for which
 * no authorization is required.
 * @see PublicApiConnection
 * @see AuthorizedApiConnection
 */
open class ShardboundServer
@JvmOverloads constructor(
        internal val applicationId: String = "F75909C6790EC382E03D0B5F78CF7D2607E9A997",
        internal val host: String = "st-george.spiritwalkgames.com",
        internal val apiVersion: Int = 1,
        internal val environment: String = "lkg"
) {

    private val apiUrl = "https://$host/api/v$apiVersion/"
    private val webClient = ClientBuilder.newClient()
    internal val defaultTarget = webClient.target(apiUrl)
    internal val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(Faction::class.java, JsonDeserializer { json, type, context -> Faction.byInternalName(json.asString) })
            .registerTypeAdapter(GameEndCondition::class.java, JsonDeserializer { json, type, context -> GameEndCondition.byCode(json.asInt) })
            .registerTypeAdapter(ApiScope::class.java, JsonDeserializer { json, type, context -> ApiScope.byName(json.asString) })
            .create()

    /**
     * Patcher api operations (versioning, download url, etc.)
     */
    val patcher = Patcher()
    /**
     * Client api operations (versioning, download url, etc.)
     */
    val client = Client()

    /**
     * Retrieve the server's manifest file
     */
    fun manifest(): Manifest {
        val target = defaultTarget.path("static_data")
        val response = target.request()
                .accept(MediaType.WILDCARD_TYPE)
                .acceptEncoding("gzip")
                .get()

        val stream = readBin(response)
        if (response.length == 0) {
            throw RuntimeException("No length for data reported")
        }

        return Manifest(stream)
    }

    /**
     * Log in with the specified credentials.
     * @param username Username to log in with
     * @param password Password to log in with
     * @return the login result, containing user id and access token
     * @throws RequestException 401 Unauthorized if either username or password is incorrect
     */
    fun login(username: String, password: String): LoginResult {
        val target = defaultTarget.path("login")
        val authBytes = "$username:$password".toByteArray(Charsets.UTF_8)
        val base64 = Base64.getEncoder().encodeToString(authBytes)

        val response = target.request()
                .accept(MediaType.WILDCARD_TYPE)
                .acceptEncoding("gzip")
                .header("Authorization", "Basic $base64")
                .post(Entity.entity("application_id=$applicationId&grant_type=password",
                        MediaType.APPLICATION_FORM_URLENCODED))

        return gson.fromJson(read(response), LoginResult::class.java)
    }

    /**
     * Upgrade this connection to an authorized connection. Functionally equivalent to calling login() followed by
     * creating an AuthorizedApiConnection with the returned access token
     */
    fun toAuthorizedConnection(username: String, password: String): AuthorizedApiConnection {
        val loginResult = login(username, password)
        return AuthorizedApiConnection(applicationId, host, apiVersion, environment, loginResult.accessToken!!)
    }

    private fun open(target: WebTarget): InputStreamReader {
        val response = target.request()
                .accept(MediaType.WILDCARD_TYPE)
                .acceptEncoding("gzip")
                .get()

        return read(response)
    }

    internal fun read(response: Response): InputStreamReader {
        return InputStreamReader(readBin(response))
    }

    internal fun readBin(response: Response): InputStream {
        if (response.status != 200) {
            throw RequestException(response.status)
        }

        val encoding: String? = response.getHeaderString("Content-Encoding")
        if (encoding == "gzip") {
            return GZIPInputStream(response.entity as java.io.InputStream)
        } else {
            return response.entity as java.io.InputStream
        }
    }

    inner class Patcher {
        private val target: WebTarget = defaultTarget.path("patcher")

        /**
         * Retrieve the current patcher version
         */
        fun version(): String {
            val type = object : TypeToken<Map<String, String>>() {}.type
            val map = gson.fromJson<Map<String, String>>(open(target.path("version/show")), type)
            return map["version"]!!
        }

        /**
         * Retrieve the download url for the latest patcher version
         */
        fun downloadUrl(): String {
            val type = object : TypeToken<Map<String, String>>() {}.type
            val target = target.path("$environment/download_url/show")
            val map = gson.fromJson<Map<String, String>>(open(target), type)
            return map["url"]!!
        }
    }

    inner class Client {
        private val target: WebTarget = defaultTarget.path("client")

        /**
         * Retrieve the current client version
         */
        fun version(): String {
            val type = object : TypeToken<Map<String, String>>() {}.type
            val target = target.path("version/$environment/show")
            val map = gson.fromJson<Map<String, String>>(open(target), type)
            return map["version"]!!
        }

        /**
         * Retrieve the download url for the latest client version
         */
        fun downloadUrl(version: String = version()): String {
            val type = object : TypeToken<Map<String, String>>() {}.type
            val target = target.path("Win64/$version/download_url/show")
            val map = gson.fromJson<Map<String, String>>(open(target), type)
            return map["url"]!!
        }
    }
}