package net.boreeas.reweave.data

/**
 * @author Malte Schütze
 */
enum class GameEndCondition(val code: Int) {
    WIN(0),
    LOSS(1),
    LOSS_CONCEDE(2),
    WIN_CONCEDE(3),
    DRAW(4);

    companion object {
        @JvmStatic
        fun byCode(code: Int): GameEndCondition {
            return values().first { it.code == code }
        }
    }
}