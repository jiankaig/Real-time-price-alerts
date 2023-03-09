UPDATE UpdatingWatchListTable
SET LastUpdateTimeStamp_UNIX = (UNIXEPOCH('now'))
WHERE SYM = "NVDA";

SELECT * FROM UpdatingWatchListTable;




