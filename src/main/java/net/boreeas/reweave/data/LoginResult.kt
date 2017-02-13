package net.boreeas.reweave.data

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author Malte Schütze
 */
data class LoginResult(
        val accessToken: String? = null,
        val expiresIn: Double = 0.0,
        val tokenType: String? = null,
        val userId: String? = null,
        @SerializedName("scope")
        val _scope: List<ApiScope>?
) {

    val scope: List<ApiScope>
        get() = _scope ?: ArrayList()
}