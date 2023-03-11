DROP TABLE IF EXISTS UpdatingWatchListTable;
CREATE TABLE UpdatingWatchListTable(
    SYM TEXT,
    Price REAL,
    CreateTimeStamp TEXT DEFAULT CURRENT_TIMESTAMP NOT NULL, 
    LastUpdateTimeStamp_UNIX TIMESTAMP
);

INSERT INTO UpdatingWatchListTable(SYM,Price,LastUpdateTimeStamp_UNIX) 
VALUES("TSLA", 0.0, (UNIXEPOCH('now')));

SELECT * FROM UpdatingWatchListTable