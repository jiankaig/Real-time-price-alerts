INSERT INTO UpdatingWatchListTable(SYM,Price,LastUpdateTimeStamp_UNIX) 
VALUES("COIN", 0, (UNIXEPOCH('now')));
SELECT * FROM UpdatingWatchListTable;