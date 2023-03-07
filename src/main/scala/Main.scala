

// Serdes
// import org.apache.kafka.streams.scala.StreamsBuilder
// import modules.CirceSerdes._
// import io.circe.{Decoder, Encoder, Printer}
// // import org.apache.kafka.streams.scala.serialization.Serdes
// import org.apache.kafka.streams.scala.serialization.Serdes._
// import org.apache.kafka.streams.scala.ImplicitConversions._

import io.github.azhur.kafkaserdecirce.CirceSupport
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.streams.{ KafkaStreams, StreamsConfig, Topology }
import org.apache.kafka.streams.scala.StreamsBuilder

// Built ins
import java.time.Duration
import java.util.Properties
import java.util.concurrent.TimeUnit

// Custom
import modules.HttpClient
import models.Data_Model

// Kafka Streams
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}
import org.apache.kafka.streams.kstream.{GlobalKTable, JoinWindows, TimeWindows, Windowed}
import org.apache.kafka.streams.scala._
import org.apache.kafka.streams.scala.kstream.{KGroupedStream, KStream, KTable}

import org.apache.kafka.clients.admin.{AdminClientConfig, ListTopicsOptions}
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.common.errors.TimeoutException
import java.util.concurrent.ExecutionException
// import scala.io


case class WatchListData(id: Int, SYM: String, Price: Int, SRC: String, CreateTimeStamp: String, LastUpdateTimeStamp_UNIX: Int)       
object StockDataApiStreaming extends App with CirceSupport{
    import io.circe.generic.auto._
    import org.apache.kafka.streams.scala.ImplicitConversions._
    import org.apache.kafka.streams.scala.Serdes._

    val SourceTopic: String = "source-topic"
    val SymbolsTopic: String = "symbols-topic"
    val ApiSinkTopic: String = "api-sink-topic"
    val PriceUpdateTopic: String = "price-update-topic"
    val PriceAlertTopic: String = "price-alert-topic"
    var continue = true
    val intervalSeconds = 10

    
    val api = new HttpClient()

    // Set up Kafka producer
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", classOf[StringSerializer].getName)
    props.put("value.serializer", classOf[StringSerializer].getName)
    val producer = new KafkaProducer[String, String](props)

    // Set up Kafka Streams configuration
    val config = new Properties()
    config.put(StreamsConfig.APPLICATION_ID_CONFIG, "api-data-producer")
    config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")

    // Check topics
    try {
        val adminClient: AdminClient = AdminClient.create(config);
        val listTopicsOptions: ListTopicsOptions = new ListTopicsOptions();
        listTopicsOptions.listInternal(true);
        listTopicsOptions.timeoutMs(3000)
        System.out.println(adminClient.listTopics(listTopicsOptions).names().get());
    } catch {
        case e: TimeoutException => println(e.getMessage())
        case e: ExecutionException => println(e.getMessage())
        // case _: Throwable => println("Got some other kind of Throwable exception")
    }

    def SendApiAction(key: String, d: WatchListData): Unit = {
        println(s"sending api to check price of: ${d.SYM} and then send via producer..")
        val http_data: String = api.run(d.SYM)
        val record = new ProducerRecord[String, String]("api-sink-topic", http_data)
        producer.send(record)
    }



    // Create a StreamsBuilder object
    val builder = new StreamsBuilder()

    //**    Topology     **//

    val watch_list_stream: KStream[String, WatchListData] = builder.stream[String, WatchListData]("source-topic").peek((_,d) => println(d))
    watch_list_stream.foreach(SendApiAction)
    watch_list_stream.to("api-sink-topic")

    // val stock_quote_data_stream = grouped_by_SYM_stream.foreach(SendApiAction)
    // stock_quote_data_stream.to(PriceUpdateTopic)

    //.peek((_,d) => println(d))

    //** End of Topology **//


    // Start the Kafka Streams application
    val streams = new KafkaStreams(builder.build(), config)
    streams.cleanUp()
    streams.start()

    // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
    sys.ShutdownHookThread {
        streams.close(Duration.ofSeconds(10))
    }
    
}