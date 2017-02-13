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
