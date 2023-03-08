DROP TABLE IF EXISTS watch_list;
CREATE TABLE IF NOT EXISTS watch_list(
    id INTEGER PRIMARY KEY AUTOINCREMENT, 
    SYM TEXT, 
    Price REAL,
    SRC TEXT, 
    CreateTimeStamp TEXT DEFAULT CURRENT_TIMESTAMP NOT NULL, 
    LastUpdateTimeStamp_UNIX TIMESTAMP DEFAULT (UNIXEPOCH('now')) NOT NULL
);
INSERT OR REPLACE INTO watch_list(SYM, Price, SRC) VALUES("IBM", 1.1, "SQLITE");

SELECT * FROM watch_list;

PRAGMA table_info(watch_list);

DROP TABLE IF EXISTS testTable;
CREATE TABLE testTable(
    id INTEGER,
    name TEXT
);
