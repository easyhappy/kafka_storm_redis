jar -xvf ../zookeeper-3.3.4.jar
jar -xvf ../kafka-0.7.1.jar
jar -xvf ../log4j-1.2.16.jar
jar -xvf ../scala-library.jar
jar -xvf ../zkclient-0.1.jar


#jar -xvf ../*.jar

rm -rf META-INF/
#cp -r ../realtime .

cp -rf ../KafkaBean.class .
#cp ../log4j.properties .
jar -cvf ../KafkaBean.jar ./*

