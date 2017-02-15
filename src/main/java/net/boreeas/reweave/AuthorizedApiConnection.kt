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

import net.boreeas.reweave.data.*
import java.util.*
import java.util.concurrent.Future
import javax.ws.rs.client.WebTarget

/**
 * An authorized api connection represents the full range of operations available to a real client.
 *
 * As with the public-scoped api connection, calls to this interface are ratelimited. The connection is backed by a
 * thread pool which executes the requests asynchronously, as such you should remember to close() the connection once it
 * is no longer required.
 * @author Malte Schütze
 * @see PublicApiConnection
 * @see ShardboundServer
 */
class AuthorizedApiConnection
@JvmOverloads constructor(applicationId: String = "F75909C6790EC382E03D0B5F78CF7D2607E9A997",
                          host: String = "st-george.spiritwalkgames.com",
                          apiVersion: Int = 1,
                          environment: String = "lkg",
                          accessToken: String)
    : PublicApiConnection(applicationId, host, apiVersion, environment, accessToken) {

    /**
     * Operations regarding this and other users
     */
    override val user: UserApiExt = UserApiExt()
    /**
     * Operations regarding houses
     */
    val house = HouseApi()
    /**
     * Operations regarding shardfalls
     */
    val expedition = ExpeditionApi()
    /**
     * Operations regarding connections to other shardbound services
     */
    val messaging = Messaging()
    /**
     * Operations regarding the preferences of the current user
     */
    val preferences = Preferences()
    /**
     * Operations regarding friends
     */
    val friends = FriendsApi()
    /**
     * Operations regarding decks
     */
    override val decks: DeckApiExt = DeckApiExt()
    /**
     * Operations regarding maps currently in rotation
     */
    val maps = MapApi()
    /**
     * Operations regarding the game itself
     */
    val game = GameApi()

    private fun <T> dump(tgt: WebTarget): Future<T> {
        return submit {
            println(open(tgt).readText())
        } as Future<T>
    }

    private fun <T> dump(tgt: WebTarget, payload: String): Future<T> {
        return submit {
            println(post(tgt, payload).readText())
        } as Future<T>
    }

    inner class UserApiExt : PublicApiConnection.UserApi() {
        /**
         * Retrieve private information about the logged in user
         */
        fun showPrivateInfo(): Future<User.PrivateData> {
            return withPrivateInfo { it }
        }

        /**
         * Apply a function to the private information about the logged in user
         */
        fun <T> withPrivateInfo(func: (User.PrivateData) -> T): Future<T> {
            return submit {
                val tgt = target.path("show_private")
                func(gson.fromJson(open(tgt), User.PrivateData::class.java))
            }
        }

        /**
         * Retrieve the welcome message shown when first logging in
         */
        fun getWelcome(): Future<WelcomeMessage> {
            return withWelcomeMessage { it }
        }

        /**
         * Apply a function to the welcome message shown when first logging in
         */
        fun <T> withWelcomeMessage(func: (WelcomeMessage) -> T): Future<T> {
            return submit {
                val tgt = target.path("welcome")
                func(gson.fromJson(open(tgt), WelcomeMessage::class.java))
            }
        }
    }

    inner class HouseApi {
        private val target = defaultTarget.path("house")

        /**
         * Retrieve the house with the specified id
         * @param houseId id of the house
         * @param includeMembers should a list of members included in the result?
         */
        fun retrieve(houseId: String, includeMembers: Boolean = false): Future<House> {
            return withHouse(houseId, includeMembers) { it }
        }

        /**
         * Apply a function to the house with the specified id
         * @param houseId id of the house
         * @param includeMembers should a list of members included in the result?
         */
        fun <T> withHouse(houseId: String, includeMembers: Boolean = false, func: (House) -> T): Future<T> {
            return submit {
                var target = target.path("show/${enc(houseId)}")
                if (includeMembers) target = target.queryParam("include_members")
                func(gson.fromJson(open(target), House::class.java))
            }
        }

        /**
         * Retrieve display information about the specified house
         */
        fun showIsland(houseId: String): Future<House.Island> {
            return withIsland(houseId) { it }
        }

        /**
         * Apply a function to the display information about the specified house
         */
        fun <T> withIsland(houseId: String, func: (House.Island) -> T): Future<T> {
            return submit {
                val target = target.path("show_island/${enc(houseId)}")
                func(gson.fromJson(open(target), House.Island::class.java))
            }
        }

        /**
         * Retrieve pending house invites for the current user
         */
        fun pendingInvites(): Future<List<Any>> {
            return withPendingInvites { it }
        }

        /**
         * Apply a function to the pending house invites for the current user
         */
        fun <T> withPendingInvites(func: (List<Any>) -> T): Future<T> {
            return submit {
                val tgt = target.path("pending_invites/show")
                val type = object : com.google.gson.reflect.TypeToken<Map<String, List<Any>>>() {}.type
                val obj = gson.fromJson<Map<String, List<Any>>>(open(tgt), type)
                func(obj["invites"]!!)
            }
        }

    }

    inner class ExpeditionApi {
        private val target = defaultTarget.path("expedition")

        /**
         * Retrieve a list of normal shardfalls currently available to the logged in user
         */
        fun normalExpeditions(): Future<Expeditions> {
            return withNormalExpeditions { it }
        }

        /**
         * Apply a function to the list of normal shardfalls currently available to the logged in user
         */
        fun <T> withNormalExpeditions(func: (Expeditions) -> T): Future<T> {
            return submit {
                val target = target.path("showall")
                func(gson.fromJson(open(target), Expeditions::class.java))
            }
        }

        /**
         * Retrieve a list of twitch integration shardfalls currently available to the logged in user
         */
        fun twitchExpeditions(priorTwitchExpeditionList: List<String> = ArrayList()): Future<Expeditions> {
            return withTwitchExpeditions { it }
        }

        /**
         * Apply a function to the list of twitch integration shardfalls currently available to the logged in user
         */
        fun <T> withTwitchExpeditions(priorTwitchExpeditionList: List<String> = ArrayList(), func: (Expeditions) -> T): Future<T> {
            return submit {
                val tgt = target.path("show/twitch")
                val map = HashMap<String, Any>()
                map["prior_twitch_expedition_list"] = priorTwitchExpeditionList
                func(gson.fromJson(post(tgt, gson.toJson(map)), Expeditions::class.java))
            }
        }
    }

    inner class Preferences {
        private val target = defaultTarget.path("preferences")

        /**
         * Retrieve the current preferences of the user
         */
        fun retrieve(): Future<Map<String, Any>> {
            return withPrefs { it }
        }

        /**
         * Apply a function to the current preferences of the user
         */
        fun <T> withPrefs(func: (Map<String, Any>) -> T): Future<T> {
            return submit {
                val tgt = target.path("showall")
                val type = object : com.google.gson.reflect.TypeToken<Map<String, Any>>() {}.type
                func(gson.fromJson<Map<String, Any>>(open(tgt), type))
            }
        }

        /**
         * Update preferences
         * @param changedValues A map containing the preferences that have been changed
         */
        fun update(changedValues: Map<String, Any>): Future<Map<String, Any>> {
            return withUpdatedPrefs(changedValues) { it }
        }

        /**
         * Update preferences and apply a function to the new set of preferences
         * @param changedValues A map containing the preferences that have been changed
         */
        fun <T> withUpdatedPrefs(changedValues: Map<String, Any>, func: (Map<String, Any>) -> T): Future<T> {
            return submit {
                val tgt = target.path("update")
                val jsonStr = gson.toJson(changedValues)
                val type = object : com.google.gson.reflect.TypeToken<Map<String, Any>>() {}.type
                func(gson.fromJson<Map<String, Any>>(post(tgt, jsonStr), type))
            }
        }
    }

    inner class Messaging {
        private val target = defaultTarget.path("messaging")

        /**
         * Retrieve connection information for the current messaging endpoint
         */
        fun getEndpoint(): Future<EndpointAddress> {
            return withEndpoint { it }
        }

        /**
         * Apply a function to connection information for the current messaging endpoint
         */
        fun <T> withEndpoint(func: (EndpointAddress) -> T): Future<T> {
            return submit {
                val target = target.path("get_endpoint")
                func(gson.fromJson(open(target), EndpointAddress::class.java))
            }
        }
    }

    inner class FriendsApi {
        private val target = defaultTarget.path("friend")

        /**
         * Retrieve a list of friends of the current user
         */
        fun showAll(): Future<List<String>> {
            return withAll { it }
        }

        /**
         * Apply a function to the list of friends of the current user
         */
        fun <T> withAll(func: (List<String>) -> T): Future<T> {
            return submit {
                val tgt = target.path("showall")
                val type = object : com.google.gson.reflect.TypeToken<Map<String, List<String>>>() {}.type
                val obj = gson.fromJson<Map<String, List<String>>>(open(tgt), type)
                func(obj["friends"]!!)
            }
        }
    }

    inner class DeckApiExt : DeckApi() {

        /**
         * Retrieve a list of decks of the current user
         */
        fun showAll(): Future<DeckList> {
            return withAll { it }
        }

        /**
         * Apply a function to the list of decks of the current user
         */
        fun <T> withAll(func: (DeckList) -> T): Future<T> {
            return submit {
                val tgt = target.path("showall")
                func(gson.fromJson(open(tgt), DeckList::class.java))
            }
        }
    }

    inner class MapApi {
        private val target = defaultTarget.path("map")

        /**
         * Retrieve a list of maps currently in use
         */
        fun showAll(): Future<MapList> {
            return withAll { it }
        }

        /**
         * Apply a function to the list of maps currently in use
         */
        fun <T> withAll(func: (MapList) -> T): Future<T> {
            return submit {
                val tgt = target.path("showall")
                func(gson.fromJson(open(tgt), MapList::class.java))
            }
        }
    }

    inner class GameApi {
        private val target = defaultTarget.path("game")

        /*
        fun joinMatchmaking(): Future<Unit> {
            return dump(target.path("matchmake"), "{}")
        }
        */
    }
}