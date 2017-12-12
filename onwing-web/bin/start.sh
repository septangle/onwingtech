#!/bin/sh

APP_ID="park.web"
APP_HOME=`pwd`
CATALINA_HOME="${APP_HOME}"

echo "appid ${APP_ID}"
echo "apphome ${APP_HOME}"
echo "catalina home: ${CATALINA_HOME}"

#MEM_OPTS="${MEM_OPTS}"
export JAVA_OPTS="$JAVA_OPTS ${MEM_OPTS}"

${CATALINA_HOME}/bin/catalina.sh run

