

import java.time.Duration
import java.util.Properties
import java.util.Calendar
import java.util.concurrent.TimeUnit
import scala.io

import java.util.Scanner

import java.time.Instant

object NonBlockingSleep {
    var continue = true
    val now = Calendar.getInstance()
    val start : Long = Instant.now.getEpochSecond
    
    
    // stop program when Ctrl-d is pressed
    def isExit(s: String): Boolean = s.headOption.map(_.toInt) == Some(4) || s == "exit"
    def evaluate(s: String) = println(s"Evaluation : $s")
    
    def main(args: Array[String]): Unit = {
        println("type 'q' and Enter to exit within the next interval")
        while(continue){
            // Check if 'q' key has been pressed
            println("send some data")

            if (System.in.available() > 0) {
                println("key q was pressed")
                val key = System.in.read()
                if (key.toChar == 'q') {
                    continue = false
                }
            }

            // Wait for specified interval before making next request
            TimeUnit.SECONDS.sleep(3)
        }
        println("exiting..")
        sys.exit() 
    }
}
