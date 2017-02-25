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

package net.boreeas.reweave

import java.net.URI

/**
 * @author Malte Schütze
 */
class RequestException : RuntimeException {
    val errorType: ErrorType
    val errorCode: Int
    val targetUri: URI

    @JvmOverloads constructor(targetUri: URI, responseCode: Int, error: ErrorType = ErrorType.getByCode(responseCode)) : super("$responseCode/$error error during request to $targetUri") {
        this.errorType = error
        this.errorCode = responseCode
        this.targetUri = targetUri
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
