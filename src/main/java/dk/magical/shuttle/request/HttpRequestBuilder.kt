package dk.magical.shuttle.request

import java.net.MalformedURLException
import java.net.URL

/**
 * Created by Christian on 04/01/2017.
 */
class HttpRequestBuilder() {

    fun build(request: HttpRequest): List<String>? {
        val url = url(request.url) ?: return null

        val requestLine = requestLine(request.method, url) ?: return null
        val headers = headers(url, request.headers)

        val result: MutableList<String> = mutableListOf()

        result.add(requestLine)
        headers.forEach { result.add(it) }
        result.add("")

        val body = request.body ?: return result
        result.add(body)

        return result
    }

    private fun requestLine(method: HttpMethod, url: URL): String? {
        val httpVersion = "HTTP/1.1"
        return "$method ${url.path} ${httpVersion}"
    }

    private fun headers(url: URL, headers: Map<String, String>): List<String> {
        val allHeaders: MutableMap<String, String> = mutableMapOf()

        allHeaders["User-Agent"] = "Shuttle/1.0"
        allHeaders["Host"] = url.host
        headers.forEach { allHeaders[it.key] = it.value }

        return allHeaders.map { "${it.key}: ${it.value}" }
    }

    private fun url(urlString: String): URL? {
        try {
            return URL(urlString)
        } catch (exception: MalformedURLException) {
            println("Malformed URL")
            return null
        }
    }
}