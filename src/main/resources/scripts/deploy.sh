#!/bin/sh
set -e
artifacts_path=$1
server_dir=$2
echo "artifacts_path:$1"
echo "server_dir:$2"

echo "remove old scripts..."
cd $server_dir
rm -rf killServer.sh
rm -rf deploy.sh

echo "copy new scripts..."
mv $artifacts_path/src/main/resources/scripts/killServer.sh  $server_dir

echo "chmod new scripts..."
chmod 777 deploy.sh
chmod 777 killServer.sh

echo "stop server..."
sh killServer.sh

echo "remove old jar..."
rm -rf *.jar

echo "copy new artifact to server directory..."
mv $artifacts_path/target/*.jar $server_dir

echo "start server ..."
cd $server_dir

java -jar *.jar 1>> log.log 2>&1&

echo "deploy done."
