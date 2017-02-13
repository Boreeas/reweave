package net.boreeas.reweave.data

/**
 * @author Malte Schütze
 */
class ExpeditionIslandPresentationData(
        val checksum: Long = 0,
        val landLevelFalloffConstant: Double = 0.0,
        val maxDistanceBetweenQuests: Double = 0.0,
        val maxDistanceQuestsInfluenceLand: Double = 0.0,
        val minDistanceBetweenQuests: Double = 0.0,
        val minQuestProgressionAngle: Double = 0.0,
        val name1: Int = 0,
        val name2: Int = 0,
        val nameNum1: Int = 0,
        val nameNum2: Int = 0,
        val nameType: Int = 0,
        val numEventNodes: Int = 0
) : IslandPresentationData() {
    override fun toString(): String {
        return "ExpeditionIslandPresentationData(checksum=$checksum, landLevelFalloffConstant=$landLevelFalloffConstant, maxDistanceBetweenQuests=$maxDistanceBetweenQuests, maxDistanceQuestsInfluenceLand=$maxDistanceQuestsInfluenceLand, minDistanceBetweenQuests=$minDistanceBetweenQuests, minQuestProgressionAngle=$minQuestProgressionAngle, name1=$name1, name2=$name2, nameNum1=$nameNum1, nameNum2=$nameNum2, nameType=$nameType, numEventNodes=$numEventNodes) inherits ${super.toString()}"
    }
}
