INSERT INTO watch_list(SYM, Price, SRC) 
VALUES("META", 0, "SQLITE");

SELECT * FROM watch_list;

INSERT INTO UpdatingWatchListTable(SYM,Price,LastUpdateTimeStamp_UNIX) 
VALUES("DEF", 3.4, 1678281999);