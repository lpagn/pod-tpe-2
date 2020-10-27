#!/bin/bash

FOLDER="/Users/luciopagni/Desktop/pod-tpe-2/server/target/tpe2-g10-server-1.0-SNAPSHOT"

#FOLDER="/home/lpagni/Escritorio/pod-tpe-2/server/target/tpe2-g10-server-1.0-SNAPSHOT"

cd $FOLDER

java  -cp 'lib/jars/*' "ar.edu.itba.pod.server.Cluster" $*
