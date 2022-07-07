#!/bin/bash

input="$1"
mysql -D "projectDB" -e "create table person_data (id smallint unsigned not null,name varchar(100),age smallint unsigned,country varchar(60),height smallint unsigned,hairColour varchar(30),primary key (id))"
while IFS= read -r line
do
        id=`echo $line | cut -d, -f1`
        name=`echo $line | cut -d, -f2`
        age=`echo $line | cut -d, -f3`
        country=`echo $line | cut -d, -f4`
        height=`echo $line | cut -d, -f5`
        hairColour=`echo $line | cut -d, -f6`
        mysql -D "projectDB" -e "insert into person_data values ($id, '${name}', $age, '${country}', $height, '${hairColour}');"
done<"$input"
