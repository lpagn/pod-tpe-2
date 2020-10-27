#!/bin/bash

echo "POD"

mvn clean package -Dmaven.test.skip=true

CODEBASE="/Users/luciopagni/Desktop/pod-tpe-2"

#CODEBASE="/home/lpagni/Escritorio/pod-tpe-2/server/target"

cd $CODEBASE/server/target

echo $CODEBASE/server/target

tar -xzvf tpe2-g10-server-1.0-SNAPSHOT-bin.tar.gz

cd $CODEBASE/client/target

echo $CODEBASE/client/target

tar -xzvf tpe2-g10-client-1.0-SNAPSHOT-bin.tar.gz

