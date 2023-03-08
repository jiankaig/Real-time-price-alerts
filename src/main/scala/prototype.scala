// import models._

// // trying out chimney
// import io.scalaland.chimney.dsl._
// import io.scalaland.chimney.partial._
// object Playground {
    
//     def main(args: Array[String]): Unit = {
//         println("Welcome to Playground")
//         // convert/transform/map
//         // Data_Model -> WatchListData
//         val sqdm: Global_Quote_Model = Global_Quote_Model(
//             `01. symbol` = "IBM",
//             `02. open` = "130.2800",
//             `03. high` = "130.4200",
//             `04. low` = "128.1900",
//             `05. price` = "128.2500",
//             `06. volume` = "3498494",
//             `07. latest trading day` = "2023-03-07",
//             `08. previous close` = "130.1900",
//             `09. change` = "-1.9400",
//             `10. change percent` = "-1.4901%"
//         )
//         val sdm = Data_Model(sqdm)
//         val timestamp: Long = System.currentTimeMillis / 1000
//         // lets instead transform sample_quote_data_model into a WatchListData
//         println(sqdm)
//         println(sqdm.`05. price`)
//         val wldm = new WatchListData(
//             99,
//             sqdm.`01. symbol`,
//             sqdm.`05. price`.toFloat,
//             "source-playground",
//             "ignore-ceate-timestamp",
//             timestamp.toInt
//         )
//         println(wldm)
//         // sample_quote_data_model.

//         print(sdm.`Global Quote`.`01. symbol`)
//         // val timestamp: Long = System.currentTimeMillis / 1000
//         // cls.`01. symbol`,
//         // cls.`05. price`.toFloat,
//         // timestamp.toInt



//     }
// }
