package dk.magical.shuttle

import com.google.common.truth.Truth
import org.junit.Test

/**
 * Created by Christian on 05/01/2017.
 */
class UrlTest {
    @Test
    fun shouldParseLocalhost() {
        val url = Url("localhost/hello/world")
        Truth.assertThat(url.host).isEqualTo("localhost")
        Truth.assertThat(url.path).isEqualTo("/hello/world")
    }

    @Test
    fun shouldParseHttpLocalhost() {
        val url = Url("http://localhost/hello/world")
        Truth.assertThat(url.host).isEqualTo("localhost")
        Truth.assertThat(url.path).isEqualTo("/hello/world")
    }

    @Test
    fun shouldParseHttpUrl() {
        val url = Url("http://google.dk/hello/world")
        Truth.assertThat(url.host).isEqualTo("google.dk")
        Truth.assertThat(url.path).isEqualTo("/hello/world")
    }

    @Test
    fun shouldParseHttpWwwUrl() {
        val url = Url("http://www.google.dk/hello/world")
        Truth.assertThat(url.host).isEqualTo("google.dk")
        Truth.assertThat(url.path).isEqualTo("/hello/world")
    }

    @Test
    fun shouldParseHttpsUrl() {
        val url = Url("https://google.dk/hello/world")
        Truth.assertThat(url.host).isEqualTo("google.dk")
        Truth.assertThat(url.path).isEqualTo("/hello/world")
    }

    @Test
    fun shouldParseHttpsWwwUrl() {
        val url = Url("https://www.google.dk/hello/world")
        Truth.assertThat(url.host).isEqualTo("google.dk")
        Truth.assertThat(url.path).isEqualTo("/hello/world")
    }
}