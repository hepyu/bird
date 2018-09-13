#!/bin/sh
broker=$1
APP_NAME=apollo-cloud-system-config
APOLLO_CLOUD_SYSTEM_CONFIG_HOME=$(dirname $(dirname `cd "$PRGDIR" >/dev/null; pwd`))"/apps/$APP_NAME"

if [ -f $APOLLO_CLOUD_SYSTEM_CONFIG_HOME/apollo-cloud-system-config-$broker.pid ]
then
        PID_FILE=$APOLLO_CLOUD_SYSTEM_CONFIG_HOME/apollo-cloud-system-config-$broker.pid
        kill `cat $PID_FILE`
        echo 'shutdown '$broker' success.'
else
        #PID_FILE=`find ${APOLLO_RUN}/servers/ -type f -name *.pid| head -n 1`
        echo 'shutdown '$broker' failed. not find pid file.'
fi
