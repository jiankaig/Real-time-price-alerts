package modules
import models.Data_Model

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
object HttpClient {
    def run(symbol: String): (String, Int, String) = {
        val url = s"https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=$symbol&apikey=VAQWPH8Y3GBKUE74"
        val response = HttpClients.createDefault.execute(new HttpGet(url))
        val data: String = EntityUtils.toString(response.getEntity)
        val status = response.getStatusLine()
        val code: Int = status.getStatusCode()
        val reason: String = status.getReasonPhrase()

        (data, code, reason)
    }

    def main(args: Array[String]): Unit = {
        var i = 0
        var http_data: String = ""
        for( i <- 1 to 10){
            val (data, code, re) = run("TSLA")
            http_data = data
            println(s"$data, $code, $re")
        }
        println("Http Data Parsing Example!")
        // Usage:
        // val e = new HttpClient()
        // val http_data: String = e.run("IBM")

        // data parsing and validation
        val result = parser.decode[Data_Model](http_data)
        result match {
            case Right(global_quote) => println(global_quote)
            case Left(error) => println(s"Error: $error")
        }
    }
}