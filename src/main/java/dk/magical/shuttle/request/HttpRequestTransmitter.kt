package dk.magical.shuttle.request

import dk.magical.shuttle.Url
import dk.magical.shuttle.response.HttpResponse
import dk.magical.shuttle.response.HttpResponseParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.Socket


/**
 * Created by Christian on 04/01/2017.
 */
class HttpRequestTransmitter() {
    val responseParser = HttpResponseParser()

    fun transmit(request: HttpRequest, port: Int, callback: (HttpResponse) -> Unit) {
        val requestBuilder = HttpRequestBuilder()
        val requestLines = requestBuilder.build(request) ?: return

        val url = Url(request.url)
        val socket = Socket(InetAddress.getByName(url.host), port)
        socket.keepAlive = true

        val printWriter = PrintWriter(socket.outputStream)
        requestLines.forEach { printWriter.println(it) }
        printWriter.flush()

        val reader = BufferedReader(InputStreamReader(socket.inputStream))
        val response = responseParser.parse(reader)

        printWriter.close()
        socket.close()

        if (response != null) {
            callback(response)
        }
    }
}