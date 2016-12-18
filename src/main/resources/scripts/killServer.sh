#!/bin/sh
PIDFile="thiki-kanban.pid"
JVM_OPTS="-Xmx2g"
SPRING_OPTS="--logging.file=application.log"

function check_if_pid_file_exists {
    if [ ! -f $PIDFile ]
    then
        echo "PID file not found: $PIDFile"
        exit 0
    fi
}

function check_if_process_is_running {
 if ps -p $(print_process) > /dev/null
 then
     return 0
 else
     return 1
 fi
}

function print_process {
    echo $(<"$PIDFile")
}
check_if_pid_file_exists
    if ! check_if_process_is_running
    then
      echo "Process $(print_process) already stopped"
      exit 0
    fi
    kill -TERM $(print_process)
    echo -ne "Waiting for process from stop"
    NOT_KILLED=1
    for i in {1..20}; do
      if check_if_process_is_running
      then
        echo -ne "."
        sleep 1
      else
        NOT_KILLED=0
      fi
    done
    echo
    if [ $NOT_KILLED = 1 ]
    then
      echo "Cannot kill process $(print_process)"
      exit 1
    fi
    echo "Process stopped"
exit 0
