#!/bin/sh
set -e
artifacts_path=$1
server_dir=$2
server_addr=$3
url=$3
echo "artifacts_path:$1"
echo "server_dir:$2"
echo "server_addr:$3"
if curl --output /dev/null --silent --head --fail "$url"; then
    echo "The server is running,stop server..."
    wget --post-data ""  $server_addr
fi

echo "clean server directory..."
cd $server_dir
rm -rf *.jar

echo "copy new artifact to server directory..."
mv $artifacts_path/*.jar $server_dir

echo "start server ..."
java -jar *.jar

echo "deploy done."
