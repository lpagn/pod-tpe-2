#!/bin/bash

echo "POD"

mvn clean package -Dmaven.test.skip=true

PATH="/Users/luciopagni/Desktop/pod-tpe-2"

#PATH="/home/lpagni/Escritorio/pod-tpe-2/server/target"

cd $PATH/server/target

tar xzf tpe2-g10-server-1.0-SNAPSHOT-bin.tar.gz

cd $PATH/client/target

tar xzf tpe2-g10-client-1.0-SNAPSHOT-bin.tar.gz

