#!/bin/sh
set -e

exec java -jar /opt/app/server.jar --spring.profiles.active=test --spring.cloud.config.enabled=false
