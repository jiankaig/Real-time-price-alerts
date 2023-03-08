package models

case class Data_Model(`Global Quote`: Global_Quote_Model)
case class Global_Quote_Model(
    `01. symbol`: String,
    `02. open`: String,
    `03. high`: String,
    `04. low`: String,
    `05. price`: String,
    `06. volume`: String,
    `07. latest trading day`: String,
    `08. previous close`: String,
    `09. change`: String,
    `10. change percent`: String
)

case class WatchListData(
    val id: Int, 
    val SYM: String, 
    val Price: Double, 
    val SRC: String, 
    val CreateTimeStamp: String, 
    val LastUpdateTimeStamp_UNIX: Int
)      
case class UpdatingWatchListData(
    val SYM: String, 
    val Price: Float, 
    val LastUpdateTimeStamp_UNIX: Int
)      