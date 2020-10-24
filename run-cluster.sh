#!/bin/bash

cd /Users/luciopagni/Desktop/pod-tpe-2/server/target/tpe2-g10-server-1.0-SNAPSHOT

java  -cp 'lib/jars/*' "ar.edu.itba.pod.server.Cluster" $*
