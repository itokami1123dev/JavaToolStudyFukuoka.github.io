mvn package

CLASSPATH=target/helloworld-1.0-SNAPSHOT.jar
CLASSPATH=$CLASSPATH:~/.m2/repository/org/apache/poi/poi/3.14/poi-3.14.jar
CLASSPATH=$CLASSPATH:~/.m2/repository/org/apache/poi/poi-ooxml/3.14/poi-ooxml-3.14.jar
CLASSPATH=$CLASSPATH:~/.m2/repository/org/apache/poi/poi-ooxml-schemas/3.14/poi-ooxml-schemas-3.14.jar
CLASSPATH=$CLASSPATH:~/.m2/repository/org/apache/xmlbeans/xmlbeans/2.6.0/xmlbeans-2.6.0.jar

export CLASSPATH

java com.example.poi.lt.App

