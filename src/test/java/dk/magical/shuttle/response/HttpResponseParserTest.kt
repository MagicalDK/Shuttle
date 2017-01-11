package dk.magical.shuttle.response

import com.google.common.truth.Truth
import dk.magical.shuttle.Status
import org.junit.Test
import java.io.BufferedReader
import java.io.StringReader

/**
 * Created by Christian on 10/01/2017.
 */
class HttpResponseParserTest {
    val responseParser = HttpResponseParser()

    @Test
    fun shouldParseStatusCode() {
        val reader = reader(listOf("HTTP/1.1 200 Message"))
        val response = responseParser.parse(reader)
        Truth.assertThat(response?.status).isEqualTo(Status.OK)
    }

    @Test
    fun shouldParseMessage() {
        val reader = reader(listOf("HTTP/1.1 200 Status Message"))
        val response = responseParser.parse(reader)
        Truth.assertThat(response?.message).isEqualTo("Status Message")
    }

    @Test
    fun shouldParseHeaders() {
        val reader = reader(listOf(
                "HTTP/1.1 200 Status Message",
                "Name: Christian Henriksen",
                "Website: www.magical.dk"
        ))
        val response = responseParser.parse(reader)
        Truth.assertThat(response?.headers).containsExactly("Name", "Christian Henriksen", "Website", "www.magical.dk")
    }

    @Test
    fun shouldParseBody() {
        val body = "This is the magical body"
        val contentLength = body.toByteArray(Charsets.UTF_8).size
        val reader = reader(listOf(
                "HTTP/1.1 200 Status Message",
                "Content-Length: $contentLength",
                "",
                body
        ))
        val response = responseParser.parse(reader)
        Truth.assertThat(response?.body).isEqualTo(body)
    }

    private fun reader(request: List<String>): BufferedReader {
        val buffer = StringBuilder()
        request.forEach { buffer.append(it).append("\n") }
        return BufferedReader(StringReader(buffer.toString()))
    }
}