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

import com.github.bucket4j.Buckets
import net.boreeas.reweave.data.*
import java.io.Closeable
import java.io.InputStreamReader
import java.net.URLEncoder
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.MediaType

/**
 * This represents the suite of operations available to public-scoped access tokens.
 *
 * Calls to this interface are ratelimited. The connection is backed by a thread pool which executes the requests
 * asynchronously, as such you should remember to close() the connection once it is no longer required.
 *
 * @author Malte Schütze
 * @see AuthorizedApiConnection
 * @see ShardboundServer
 */
open class PublicApiConnection
@JvmOverloads constructor(applicationId: String = "F75909C6790EC382E03D0B5F78CF7D2607E9A997",
                          host: String = "st-george.spiritwalkgames.com",
                          apiVersion: Int = 1,
                          environment: String = "lkg",
                          private val accessToken: String)
    : ShardboundServer(applicationId, host, apiVersion, environment), AutoCloseable, Closeable {


    var bucket = Buckets.withMillisTimePrecision()
            .withLimitedBandwidth(100, TimeUnit.SECONDS, 1)
            .build()

    private val pool = Executors.newCachedThreadPool { target ->
        val thread = Thread(target, "AuthorizedApiConnection Worker Thread")
        thread.isDaemon = true
        thread
    }

    internal fun enc(tgt: String): String = URLEncoder.encode(tgt, "UTF-8")

    override fun close() {
        pool.shutdownNow()
    }

    internal fun open(target: WebTarget): InputStreamReader {
        val response = target.request()
                .accept(MediaType.WILDCARD_TYPE)
                .acceptEncoding("gzip")
                .header("Authorization", "Bearer $accessToken")
                .get()

        return read(response)
    }

    internal fun post(target: WebTarget, payload: String, mediaType: MediaType = MediaType.APPLICATION_JSON_TYPE): InputStreamReader {
        val response = target.request()
                .accept(MediaType.WILDCARD_TYPE)
                .acceptEncoding("gzip")
                .header("Authorization", "Bearer $accessToken")
                .post(Entity.entity(payload, mediaType))

        return read(response)
    }

    internal fun <T> submit(func: () -> T): Future<T> {
        return pool.submit<T> {
            bucket.consume(1)
            func()
        }
    }

    /**
     * User related operations
     */
    open val user = UserApi()
    /**
     * Deck related operations
     */
    open val decks = DeckApi()
    /**
     * OAuth related operations
     */
    val oauth = OAuth()

    open inner class UserApi {
        internal val target = defaultTarget.path("user")

        /**
         * Retrieve information about the specified user
         * @param userId The id of the user
         */
        fun retrieve(userId: String): Future<User> {
            return submit {
                val target = target.path("show/${enc(userId)}")
                gson.fromJson(open(target), User::class.java)
            }
        }

        /**
         * Retrieve the match history of the specified user
         * @param userId The id of the user
         */
        fun getMatchHistory(userId: String): Future<List<Game>> {
            return submit {
                val tgt = target.path("history/show/${enc(userId)}")
                val obj = gson.fromJson(open(tgt), GameList::class.java)
                obj.games
            }
        }
    }

    open inner class DeckApi {
        internal val target = defaultTarget.path("deck")

        /**
         * Retrieve all decks used by the specified user
         * @param userId The id of the user
         */
        fun showAll(userId: String): Future<DeckList> {
            return submit {
                val tgt = target.path("showall/${enc(userId)}")
                gson.fromJson(open(tgt), DeckList::class.java)
            }
        }
    }

    inner class OAuth {
        internal val target = ClientBuilder.newClient().target("https://$host/oauth")

        /**
         * Re-retrieve token information
         */
        fun verifyCredentials(): Future<LoginResult> {
            return submit {
                val tgt = target.path("verify_credentials")
                gson.fromJson(open(tgt), LoginResult::class.java)
            }
        }

        /**
         * Revoke the currently used api token. This invalidates the connection: All future requests will fail.
         * Therefore, the threadpool backing the requests is scheduled to shut down and any further calls will fail.
         *
         * Note that this operation is not possible if this is a native-scoped access token
         * @throws RequestException 405 Method Not Allowed - if the current token is a native-scoped token
         */
        fun revoke(): Future<Unit> {
            return submit {
                val tgt = target.path("revoke")
                open(tgt)
                pool.shutdown()
            }
        }
    }
}