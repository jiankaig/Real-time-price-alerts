package modules
import models.Api_Output_Model

import org.apache.http.impl.client.HttpClients
import org.apache.http.client.methods.HttpGet
import org.apache.http.util.EntityUtils

// Data parsing and validation
import io.circe.parser
import io.circe.generic.auto._

class HttpClient {
    def run(symbol: String): (String,Int,String) = {
        val url = s"https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=$symbol&apikey=VAQWPH8Y3GBKUE74"
        val response = HttpClients.createDefault.execute(new HttpGet(url))
        val data: String = EntityUtils.toString(response.getEntity)
        val status = response.getStatusLine()
        val code: Int = status.getStatusCode()
        val reason: String = status.getReasonPhrase()

        (data,code,reason)
    }
}
