package modules
import models.Data_Model

import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.util.EntityUtils

// Data parsing and validation
import io.circe.parser
import io.circe.generic.auto._

class HttpClient {

    val httpClient = new DefaultHttpClient()
    
    def run(symbol: String): String = {
        val url = s"https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=$symbol&apikey=VAQWPH8Y3GBKUE74"
        val response = HttpClients.createDefault.execute(new HttpGet(url))
        val data = EntityUtils.toString(response.getEntity)

        // val httpResponse = httpClient.execute(new HttpGet(url))
        // val entity = httpResponse.getEntity()
        // // val content = entity.getContent()
        // // val str = content.toString()
        // val str = EntityUtils.toString(entity, "UTF-8")

        return data
    }

    def main(args: Array[String]): Unit = {
        println("Http Data Parsing Example!")
        val e = new HttpClient()
        val http_data: String = e.run("IBM")
        // println(http_data)

        // data parsing and validation
        val result = parser.decode[Data_Model](http_data)
        result match {
            case Right(global_quote) => println(global_quote)
            case Left(error) => println(s"Error: $error")
        }
    }
}