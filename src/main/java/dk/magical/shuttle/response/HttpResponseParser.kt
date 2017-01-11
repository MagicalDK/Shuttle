package dk.magical.shuttle.response

import dk.magical.shuttle.Status
import java.io.BufferedReader

/**
 * Created by Christian on 10/01/2017.
 */
class HttpResponseParser {
    fun parse(reader: BufferedReader): HttpResponse? {
        val lines = readLines(reader)

        val responseLine = lines.first()
        val statusCode = statusCode(responseLine) ?: return null
        val statusMessage = statusMessage(responseLine)
        val headers = headers(lines.drop(1))

        val contentLengthHeader = headers["Content-Length"] ?: "0"
        val contentLength = stringToInt(contentLengthHeader)
        val body = body(reader, contentLength)

        return HttpResponse(statusCode, statusMessage, headers, body)
    }

    private fun readLines(reader: BufferedReader): List<String> {
        val lines: MutableList<String> = mutableListOf()
        while (true) {
            val line = reader.readLine()
            if (line == null || line.isEmpty())
                break

            lines.add(line)
        }

        return lines
    }

    private fun statusCode(responseLine: String): Status? {
        val elements = responseLine.split(" ")
        val status = stringToInt(elements[1]) ?: return null

        return Status.statusFromCode(status)
    }

    private fun statusMessage(responseLine: String): String {
        val elements = responseLine.split(" ", limit = 3)
        return elements[2]
    }

    private fun headers(headers: List<String>): Map<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        headers.forEach { string ->
            val header = string.split(":", limit = 2)
            map.put(header.first(), header.last().trimStart())
        }
        return map
    }

    private fun body(reader: BufferedReader, contentLength: Int?): String? {
        if (contentLength == null)
            return null

        val chars = CharArray(contentLength)
        val numberOfChars = reader.read(chars)
        return String(chars, 0, numberOfChars)
    }

    private fun stringToInt(string: String): Int? {
        try {
            return string.toInt()
        } catch (e: NumberFormatException) {
            return null
        }
    }
}