package dk.magical.shuttle

import dk.magical.loom.Loom
import dk.magical.loom.Status
import dk.magical.loom.routing.Router
import dk.magical.shuttle.request.HttpMethod
import dk.magical.shuttle.request.HttpRequest
import dk.magical.shuttle.request.HttpRequestTransmitter
import dk.magical.shuttle.response.HttpResponse
import java.util.concurrent.Executors

/**
 * Created by Christian on 04/01/2017.
 */
class Shuttle {
    fun get(url: String, port: Int = 80): RequestChain = RequestChain(HttpMethod.GET, Url(url), port)
    fun post(url: String, port: Int = 80): RequestChain = RequestChain(HttpMethod.POST, Url(url), port)
    fun put(url: String, port: Int = 80): RequestChain = RequestChain(HttpMethod.PUT, Url(url), port)
    fun delete(url: String, port: Int = 80): RequestChain = RequestChain(HttpMethod.DELETE, Url(url), port)
}

class RequestChain(val method: HttpMethod, val url: Url, val port: Int) {
    val transmitter = HttpRequestTransmitter()

    var headers: MutableMap<String, String> = mutableMapOf()
    var body: String? = null

    fun header(key: String, value: String) {
        this.headers[key] = value
    }

    fun body(body: String) {
        this.body = body
    }

    fun call(callback: (HttpResponse) -> Unit) {
        val request = HttpRequest(method, url.urlString, headers, body)
        transmitter.transmit(request, port, callback)
    }
}

fun main(args: Array<String>) {
    listenForRequests()

    val transmitter = HttpRequestTransmitter()
    val request = HttpRequest(HttpMethod.GET, "http://localhost/hello/world", mapOf(), "")
    transmitter.transmit(request, 8080) { response ->
        println("Response:")
        println(response)
    }
}

fun listenForRequests() {
    val loom = Loom(Executors.newCachedThreadPool())

    val router = Router("/hello")
    router.get("/world") { request, response ->
        println("Request: $request")
        response.status(Status.OK).body("Hello!").end()
    }

    loom.route(router)
    loom.start(8080) { message, response ->
        println("ERROR - $message")
    }
}
