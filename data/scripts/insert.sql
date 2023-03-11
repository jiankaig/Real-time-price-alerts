INSERT INTO UpdatingWatchListTable(SYM,Price,LastUpdateTimeStamp_UNIX) 
VALUES("AAPL", 0, (UNIXEPOCH('now')));

SELECT * FROM UpdatingWatchListTable;