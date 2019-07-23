#!/bin/sh
set -e

JAVA_OPTS="-javaagent:/opt/appdynamics/app-agent/javaagent.jar"

exec java $JAVA_OPTS -jar /opt/app/server.jar --spring.profiles.active=test --spring.cloud.config.enabled=false
