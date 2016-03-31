#FROM ubuntu:14.04
#RUN apt-get update && apt-get install -y curl \
#git \
#vim 
#VOLUME  ["/Users/vipinjoshi/Desktop/GitHub/DockerTest/NSampleAuth","/var/src"]
#RUN mvn src/pom.xml
#COPY tomcat.war /var/lib/tomcat8/webapps
#Restart tomcat
#RUN apt-get install -y maven

#WORKDIR /code

# Prepare by downloading dependencies
#ADD pom.xml /code/pom.xml  
#RUN ["mvn", "dependency:resolve"]  
#RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar
#ADD src /code/src  
#RUN ["mvn", "package"]

#EXPOSE 4567  
#CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "target/sparkexample-jar-with-dependencies.jar"] 


FROM ubuntu:14.04

RUN apt-get update && \
    apt-get install -y  software-properties-common && \
    add-apt-repository ppa:webupd8team/java -y && \
    apt-get update && \
    echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get install -y oracle-java8-installer \
    tomcat7

RUN apt-get update && apt-get install -y maven \
vim 

VOLUME  ["/NSampleAuth","/var/src"]

## Prepare by downloading dependencies
#WORKDIR /Users/vipinjoshi/Documents/workspace/NSampleAuth
ADD ./pom.xml /var/src/pom.xml  
WORKDIR /var/src

RUN ["mvn", "dependency:resolve"]  
RUN ["mvn", "verify"]

ADD src /var/src  
RUN ["mvn", "package"]

EXPOSE 8080  
#CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-war", "target/NSampleAuth.war"]  
#WORKDIR /Users/vipinjoshi/Documents/workspace/NSampleAuth/target
ADD ./target/NSampleAuth.war /var/lib/tomcat7/webapps
RUN restart tomcat7
