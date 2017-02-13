package net.boreeas.reweave.data

/**
 * Represents the scope that an api token is allowed to access
 * @author Malte Schütze
 */
enum class ApiScope(val repr: String) {
    /**
     * Native scope: Full end-user permissions
     */
    NATIVE("native"),
    /**
     * Public scope: Limited permissions
     */
    PUBLIC("public");

    companion object {
        @JvmStatic
        fun byName(repr: String): ApiScope {
            return values().first { it.repr == repr }
        }
    }
}