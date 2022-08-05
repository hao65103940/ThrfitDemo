
#mvn clean package  --settings /Users/fit2cloud/apache-maven-3.6.2/conf/settings-physical.xml  -Dmaven.test.skip=true

rm -rf ms-thrift-server.tar
docker build -t ms-thrift-server:1.0 .

docker save -o ms-thrift-server.tar ms-thrift-server:1.0
