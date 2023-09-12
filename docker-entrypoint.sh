#!/bin/bash
set -e

if [ "$1" != '--' ]; then
  mvn test -pl best-practice -Dtest=DesktopTests
else
  shift
  exec $@
fi
