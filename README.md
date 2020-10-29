# Programación de Objetos Distribuidos

### Primeros pasos
Es necesario correr la línea `mvn clean package install`, posteriormente descomprimir los archivos .tar.gz que se generan en las carpetas `/client/target` y `/server/target`.

### Levantando el Cluster
Primero hay que dirigirse a la carpeta `/server/target/tpe2-g10-server-1.0-SNAPSHOT` y correr el archivo `run-cluster.sh` con el parámetro de la máscara de red a utilizar para levantar el Cluster (si no se especifica una se defaultea a 10.16.1.* , también pueden pasarse varias separándolas con un `;`). Un ejemplo de invocación:

./run-cluster.sh -Dinterfaces="192.168.1.*"

### Utilizando los clientes
Hay que dirigirse a la carpeta `/client/target/tpe2-g10-client-1.0-SNAPSHOT`. Allí tenemos cinco archivos bash para correr, con el nombre en concordancia con las queries solicitadas por la cátedra. A continuación algunos usos de los mismos:

#### Para realizar la Query 1 con el Cluster en 127.0.0.1:5701, tomando los datos de Buenos Aires de la carpeta actual y generando los archivos de salida también en la carpeta local:
./query1.sh -Dcity="BUE" -Daddresses="127.0.0.1:5701" -DinPath="." -DoutPath="."

#### Para realizar la Query 2 con el Cluster en 127.0.0.1:5701, tomando los datos de Buenos Aires de la carpeta actual, generando los archivos de salida también en la carpeta local y tomando el mínimo de árboles por barrio igual a 100:
./query2.sh -Dcity="BUE" -Daddresses="127.0.0.1:5701" -DinPath="." -DoutPath="." -Dmin=100

#### Para realizar la Query 3 con el Cluster en 127.0.0.1:5701, tomando los datos de Buenos Aires de la carpeta actual, generando los archivos de salida también en la carpeta local y mostrando las primeras 5 especies según diámetro:
./query3.sh -Dcity="BUE" -Daddresses="127.0.0.1:5701" -DinPath="." -DoutPath="." -Dn=5

#### Para realizar la Query 4 con el Cluster en 127.0.0.1:5701, tomando los datos de Buenos Aires de la carpeta actual, generando los archivos de salida también en la carpeta local y mostrando los pares de barrios que tengan más de 11000 ejemplares de la especie "Fraxinus pennsylvanica":
./query4.sh -Dcity="BUE" -Daddresses="127.0.0.1:5701" -DinPath="." -DoutPath="." -Dmin=11000 -Dname="Fraxinus pennsylvanica"

#### Para realizar la Query 5 con el Cluster en 127.0.0.1:5701, tomando los datos de Buenos Aires de la carpeta actual y generando los archivos de salida también en la carpeta local:
./query5.sh -Dcity="BUE" -Daddresses="127.0.0.1:5701" -DinPath="." -DoutPath="."
