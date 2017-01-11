package dk.magical.shuttle.response

import dk.magical.shuttle.Status

/**
 * Created by Christian on 10/01/2017.
 */
data class HttpResponse(val status: Status, val message: String, val headers: Map<String, String>, val body: String?)