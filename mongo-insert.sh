#!/bin/bash

input="output4.csv"
while IFS=',' read -r line || [[ -n "$line" ]]; do
        line=${line//$','/,}
        IFS=',' read -r -a var <<< "$line"
        mongo projectDB  --eval 'db.personData.insert({id: "'"${var[0]}"'", name: "'"${var[1]}"'", age: "'"${var[2]}"'",
        country:"'"${var[3]}"'",height:"'"${var[4]}"'",hairColour:"'"${var[5]}"'"})'
done < "$input"
