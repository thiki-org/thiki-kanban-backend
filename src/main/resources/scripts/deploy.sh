#!/usr/bin/env bash
server_host=$1
server_dir=$2

echo "-> upload artifact to remote server"
scp /Users/xubt/Documents/products/thiki-kanban-backend/build/libs/kanban-1.0-SNAPSHOT.jar $server_host:$server_dir
scp killServer.sh $server_host:$server_dir

echo "-> login remote server"
ssh server <<'ENDSSH'
cd $server_dir

echo "-> stop server"
sh killServer.sh

echo "-> start server"
nohup java -jar kanban-1.0-SNAPSHOT.jar
echo "-> everything is done."

exit.
ENDSSH