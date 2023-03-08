UPDATE watch_list
SET Price = 13,
    LastUpdateTimeStamp_UNIX = (UNIXEPOCH('now'))
WHERE SYM = "IBM";

SELECT * FROM watch_list;




