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

package net.boreeas.reweave.data

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author Malte Schütze
 */
data class Quest(
        val completionStatus: CompletionStatus? = null,
        @SerializedName("contributors")
        val _contributors: Map<String, PlayerContribution>?,
        val expeditionId: String? = null,
        val location: Double = 0.0,
        val matchmakingPartners: Any? = null,
        val nodeStatus: Int = 0,
        @SerializedName("presentation_data")
        val _presentationData: PresentationData?,
        val questClass: String? = null,
        val questId: String? = null,
        val questNodeSlot: Node? = null,
        val topContributor: PlayerContribution? = null,
        @SerializedName("user_submitted_content")
        val _userSubmittedContent: Map<String, Any>?
) {
    val contributors: Map<String, PlayerContribution>
        get() = _contributors ?: HashMap()

    val presentationData: PresentationData
        get() = PresentationData(HashMap())

    val userSubmittedContent: Map<String, Any>
        get() = _userSubmittedContent ?: HashMap()


    data class PresentationData(
            @SerializedName("named_parameters")
            val _namedParameters: Map<String, Any>?
    ) {

        val namedParameters: Map<String, Any>
            get() = _namedParameters ?: HashMap()

        override fun toString(): String {
            return "PresentationData(namedParameters=$namedParameters)"
        }
    }

    data class CompletionStatus(
            val goal: Double = 0.0,
            val progress: Double = 0.0
    )

    data class Node(
            val index: Int = 0,
            val rarity: Int = 0,
            val start: Boolean = false,
            val activeQuestType: Boolean = false
    )

    data class PlayerContribution(
            val count: Int = 0,
            val displayName: String? = null
    )

    override fun toString(): String {
        return "Quest(completionStatus=$completionStatus, contributors=$contributors, expeditionId=$expeditionId, location=$location, matchmakingPartners=$matchmakingPartners, nodeStatus=$nodeStatus, presentationData=$presentationData, questClass=$questClass, questId=$questId, questNodeSlot=$questNodeSlot, topContributor=$topContributor, userSubmittedContent=$userSubmittedContent)"
    }
}
