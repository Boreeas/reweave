package net.boreeas.reweave.data

/**
 * @author Malte Schütze
 */
data class Game(
        val adjustedEndCondition: GameEndCondition?,
        val gameId: String?,
        val opponentDisplayName: String?,
        val opponentId: String?,
        val startDate: String?
)