#!/bin/sh
set -e
env_params_path=$2

echo "env_params_path:$2"

source $2

echo "init database $database_name_sit ..."
mysql -u "$username_sit" "-p$password_sit" -e  "DROP DATABASE  IF EXISTS $database_name_sit;"
mysql -u "$username_sit" "-p$password_sit" -e  "CREATE DATABASE $database_name_sit DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;"
mysql -u "$username_sit" "-p$password_sit" -e  "USE $database_name_sit;"

echo "current path:"
pwd

mysql -u "$username_sit" "-p$password_sit" "$database_name_sit" < "src/main/resources/scripts/db/mysql_init.sql"

echo "init database done."
