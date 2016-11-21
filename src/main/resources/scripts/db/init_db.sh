#!/bin/sh
set -e
username=$1
password=$2
database_name=$3
host_name=$4

echo "init database $database_name ..."
mysql -u "$username" "-p$password" -e  "DROP DATABASE  IF EXISTS $database_name;"
mysql -u "$username" "-p$password" -e  "CREATE DATABASE $database_name DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;"
mysql -u "$username" "-p$password" -e  "USE $database_name;"

echo "current path:"
pwd

mysql -u "$username" "-p$password" "$database_name" < "src/main/resources/scripts/db/mysql_init.sql"

echo "init database done."

echo "init database properties ..."

echo "jdbc.url=jdbc:mysql://$host_name:3306/thiki-kanban?useUnicode=true&characterEncoding=utf8" >>../../kanban.properties
echo "jdbc.username=$username" >>../../kanban.properties
echo "jdbc.password=$password" >>../../kanban.properties

echo "init database properties done."
