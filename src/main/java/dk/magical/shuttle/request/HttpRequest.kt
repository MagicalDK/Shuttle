package dk.magical.shuttle.request

/**
 * Created by Christian on 04/01/2017.
 */
data class HttpRequest(val method: HttpMethod, val url: String, val headers: Map<String, String>, val body: String?)

enum class HttpMethod {
    GET, POST, PUT, DELETE;

    override fun toString(): String {
        when(this) {
            GET -> return "GET"
            POST -> return "POST"
            PUT -> return "PUT"
            DELETE -> return "DELETE"
        }
    }
}
