package net.boreeas.reweave

/**
 * @author Malte Schütze
 */
class RequestException : RuntimeException {
    val errorType: ErrorType
    val errorCode: Int

    @JvmOverloads constructor(responseCode: Int, error: ErrorType = ErrorType.getByCode(responseCode)) : super(responseCode.toString() + "/" + error + " error during request") {
        this.errorType = error
        this.errorCode = responseCode
    }


    enum class ErrorType constructor(val code: Int) {
        BAD_REQUEST(400),
        UNAUTHORIZED(401),
        NOT_FOUND(404),
        METHOD_NOT_ALLOWED(405),
        UNSUPPORTED_MEDIA_TYPE(415),
        RATE_LIMIT_EXCEEDED(429),
        INTERNAL_SERVER_ERROR(500),
        SERVICE_UNAVAILABLE(503),
        CLOUDFLARE_GENERIC(520),
        CLOUDFLARE_CONNECTION_REFUSED(521),
        CLOUDFLARE_TIMEOUT(522),
        CLOUDFLARE_SSL_HANDSHAKE_FAILED(525),
        UNKNOWN_ERROR_TYPE(0);

        companion object {
            @JvmStatic
            fun getByCode(code: Int): ErrorType {
                return values().firstOrNull { it.code == code } ?: UNKNOWN_ERROR_TYPE
            }
        }
    }
}
