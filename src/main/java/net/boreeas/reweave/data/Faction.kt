package net.boreeas.reweave.data

/**
 * @author Malte Schütze
 */
enum class Faction(val internalName: String) {
    STEELSINGER("BigCreatureFaction"), // Petra, red
    FATEKEEPER("ControlFaction"), // Mori, blue
    LANDSHAPER("EarthbenderFaction"), // Vardan, green
    BLOODBINDER("SacrificeFaction"), // Sabine, purple,
    PACKRUNNER("SwarmFaction"), // Juro, yellow
    WAYFINDER("DirectBurnFaction"); // Wynn, orange

    companion object {
        @JvmStatic
        fun byInternalName(name: String): Faction? {
            return Faction.values().find {
                faction ->
                faction.internalName.equals(name, ignoreCase = true)
            }
        }
    }
}