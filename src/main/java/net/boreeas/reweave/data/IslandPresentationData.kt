package net.boreeas.reweave.data

/**
 * @author Malte Schütze
 */
open class IslandPresentationData(
        val baseIslandSeed: Long = 0,
        val bottomHeight: Double = 0.0,
        val bottomHeightVariancePercent: Double = 0.0,
        val desertMoisturePercent: Double = 0.0,
        val elevationVariancePercent: Double = 0.0,
        val forestElevationPercent: Double = 0.0,
        val gainPercent: Double = 0.0,
        val islandFeaturesSeed: Long = 0,
        val lacunarityPercent: Double = 0.0,
        val maxElevation: Double = 0.0,
        val maxIslandWidth: Double = 0.0,
        val numLloydRelaxations: Int = 0,
        val numOctaves: Int = 0,
        val numVoronoiPoints: Int = 0,
        val snowElevationPercent: Double = 0.0,
        val voronoiResolution: Double = 0.0,
        val waterFactorPercent: Double = 0.0,
        val waterLevelConstant: Double = 0.0,
        val waterLevelFalloffPercent: Double = 0.0
) {
    override fun toString(): String {
        return "IslandPresentationData(baseIslandSeed=$baseIslandSeed, bottomHeight=$bottomHeight, bottomHeightVariancePercent=$bottomHeightVariancePercent, desertMoisturePercent=$desertMoisturePercent, elevationVariancePercent=$elevationVariancePercent, forestElevationPercent=$forestElevationPercent, gainPercent=$gainPercent, islandFeaturesSeed=$islandFeaturesSeed, lacunarityPercent=$lacunarityPercent, maxElevation=$maxElevation, maxIslandWidth=$maxIslandWidth, numLloydRelaxations=$numLloydRelaxations, numOctaves=$numOctaves, numVoronoiPoints=$numVoronoiPoints, snowElevationPercent=$snowElevationPercent, voronoiResolution=$voronoiResolution, waterFactorPercent=$waterFactorPercent, waterLevelConstant=$waterLevelConstant, waterLevelFalloffPercent=$waterLevelFalloffPercent)"
    }
}
