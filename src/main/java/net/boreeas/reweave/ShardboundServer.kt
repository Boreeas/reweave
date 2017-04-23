/*
 *    Copyright 2017 Malte Schütze
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.boreeas.reweave

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.reflect.TypeToken
import net.boreeas.reweave.data.*
import java.io.InputStream
import java.io.InputStreamReader
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
        internal val applicationId: String = "21e217345069412b8d860251944489ae",
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

        val stream = try {
            readBin(response)
        } catch (ex: RequestException) {
            throw RequestException(target.uri, ex.errorCode, ex.errorType)
        }
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
    fun login(steamTicket: String): LoginResult {
        val target = defaultTarget.path("login/steam")
        // val authBytes = "$username:$password".toByteArray(Charsets.UTF_8)
        // val base64 = Base64.getEncoder().encodeToString(authBytes)

        val response = target.request()
                .accept(MediaType.WILDCARD_TYPE)
                .acceptEncoding("gzip")
                // .header("Authorization", "Basic $base64")
                .post(Entity.entity("application_id=$applicationId&steam_ticket=$steamTicket",
                        MediaType.APPLICATION_FORM_URLENCODED))

        val stream = try {
            read(response)
        } catch (ex: RequestException) {
            throw RequestException(target.uri, ex.errorCode, ex.errorType)
        }
        return gson.fromJson(stream, LoginResult::class.java)
    }

    /**
     * Upgrade this connection to an authorized connection. Functionally equivalent to calling login() followed by
     * creating an AuthorizedApiConnection with the returned access token
     */
    fun toAuthorizedConnection(steamTicket: String): AuthorizedApiConnection {
        val loginResult = login(steamTicket)
        return AuthorizedApiConnection(applicationId, host, apiVersion, environment, loginResult.accessToken!!)
    }

    private fun open(target: WebTarget): InputStreamReader {
        val response = target.request()
                .accept(MediaType.WILDCARD_TYPE)
                .acceptEncoding("gzip")
                .get()

        return try {
            read(response)
        } catch (ex: RequestException) {
            throw RequestException(target.uri, ex.errorCode, ex.errorType)
        }
    }

    internal fun read(response: Response): InputStreamReader {
        return InputStreamReader(readBin(response))
    }

    internal fun readBin(response: Response): InputStream {
        if (response.status != 200) {
            throw RequestException(null, response.status)
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