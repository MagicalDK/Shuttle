package dk.magical.shuttle

/**
 * Created by Christian on 05/01/2017.
 */
data class Url(val urlString: String) {
    val urlWithoutProtocol: String get() = urlString.removePrefix("http://").removePrefix("https://")
    val urlWithoutProtocolAndWww: String get() =  urlWithoutProtocol.removePrefix("www.")
    val hostEndIndex: Int get() =  urlWithoutProtocolAndWww.indexOfFirst { it == '/' }

    val host: String get() {
        return urlWithoutProtocolAndWww.removeRange(hostEndIndex, urlWithoutProtocolAndWww.lastIndex + 1)
    }

    val path: String get() {
        return urlWithoutProtocolAndWww.removeRange(0, hostEndIndex)
    }
}