#!/bin/bash
echo "running kcat.sh!"
kafkacat -b kafka:9092 -q -u -X auto.offset.reset=earliest -t price-update-topic | \
jq --compact-output --unbuffered \
    '. |
    {   schema: { type: "struct", optional: false, fields: [
                { type: "string", optional: true, field:"SYM"},
                { type :"float", optional: true, field:"Price"},
                { type :"int64", optional: true, field:"LastUpdateTimeStamp_UNIX"}]},
        payload: {
            SYM: .SYM,
            Price: .Price,
            LastUpdateTimeStamp_UNIX: .LastUpdateTimeStamp_UNIX
            }
    }' | \
kafkacat -b kafka:9092 -t price-update-topic-schema -P -T -u | jq --unbuffered '.'