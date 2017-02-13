package net.boreeas.reweave.data

/**
 * @author Malte Schütze
 */
data class User(
        val activeGame: String? = null,
        val displayName: String? = null,
        val houseId: String? = null,
        val houseShardfallExpedition: String? = null,
        val isDev: Boolean = false,
        val questsCompleted: Int = 0,
        val status: Int = 0,
        val twitchDisplayName: String? = null,
        val twitchId: Int? = null,
        val userId: String? = null,
        val wins: Int = 0
) {

    data class PrivateData(
            val acceptedEula: Boolean = false,
            val lastReroll: String? = null,
            val userId: String? = null
    )
}