# Data Modelling

we model data in watch list to store and update stock prices. 

using sqlite3 as a lightweight prototype db. the db will be in sync with a 
kafka topic using kafka connect. 

## Create sqlite database
run:
` sqlite3 data/watch_list.db '.read data/create.sql' `

## Update db
run:
` sqlite3 data/watch_list.db '.read data/update.sql' `