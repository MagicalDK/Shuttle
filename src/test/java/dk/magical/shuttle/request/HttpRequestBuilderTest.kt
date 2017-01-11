package dk.magical.shuttle.request

import com.google.common.truth.Truth
import dk.magical.shuttle.request.HttpMethod
import dk.magical.shuttle.request.HttpRequest
import dk.magical.shuttle.request.HttpRequestBuilder
import org.junit.Test
import kotlin.test.fail

/**
 * Created by Christian on 04/01/2017.
 */
class HttpRequestBuilderTest {
    val builder = HttpRequestBuilder()

    @Test
    fun shouldBuildRequestLine() {
        val request = HttpRequest(HttpMethod.GET, "http://www.magical.dk/hello/world", mapOf(), null)
        val requestLines = builder.build(request) ?: fail("Null request string")
        Truth.assertThat(requestLines.first()).isEqualTo("GET /hello/world HTTP/1.1")
    }

    @Test
    fun shouldAddUserAgentHeader() {
        val request = HttpRequest(HttpMethod.GET, "http://www.magical.dk/hello/world", mapOf(), null)
        val requestLines = builder.build(request) ?: fail("Null request string")
        Truth.assertThat(requestLines).contains("User-Agent: Shuttle/1.0")
    }

    @Test
    fun shouldAddHostHeader() {
        val request = HttpRequest(HttpMethod.GET, "http://www.magical.dk/hello/world", mapOf(), null)
        val requestLines = builder.build(request) ?: fail("Null request string")
        Truth.assertThat(requestLines).contains("Host: www.magical.dk")
    }

    @Test
    fun shouldAddHeaders() {
        val request = HttpRequest(HttpMethod.GET, "http://www.magical.dk/hello/world", mapOf("Website" to "Magical.dk"), null)
        val requestLines = builder.build(request) ?: fail("Null request string")
        Truth.assertThat(requestLines).contains("Website: Magical.dk")
    }

    @Test
    fun shouldAddBody() {
        val request = HttpRequest(HttpMethod.GET, "http://www.magical.dk/hello/world", mapOf(), "The magical body")
        val requestLines = builder.build(request) ?: fail("Null request string")
        Truth.assertThat(requestLines.contains("The magical body"))
    }
}