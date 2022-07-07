#!/bin/bash

cp output4.csv output5.csv
while IFS=',' read -r line || [[ -n "$line" ]]; do
        line=${line//$','/,}
        IFS=',' read -r -a columns <<< "$line"
        code=${columns[3]}
        value=$(grep $code dictionary.csv | cut -d"&" -f3)
        if [ ! -z "$value" ]
        then
                sed -i "s|${code}|${value}|g" output4.csv
        fi
done < output4.csv
