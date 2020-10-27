#!/bin/bash

echo "POD"

mvn clean package -Dmaven.test.skip=true

cd /home/lpagni/Escritorio/pod-tpe-2/server/target
tar xzf tpe2-g10-server-1.0-SNAPSHOT-bin.tar.gz

#chmod u+x ./tpe2-g10-server-1.0-SNAPSHOT/run-registry.sh

#chmod u+x ./tpe2-g10-server-1.0-SNAPSHOT/run-server.sh

cd /home/lpagni/Escritorio/pod-tpe-2/client/target/

tar xzf tpe2-g10-client-1.0-SNAPSHOT-bin.tar.gz
