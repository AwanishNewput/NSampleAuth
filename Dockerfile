FROM ubuntu:14.04

RUN apt-get update && \
    apt-get install -y  software-properties-common && \
    add-apt-repository ppa:webupd8team/java -y && \
    apt-get update && \
    echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get install -y oracle-java8-installer \
    tomcat7 \
    openjdk-6-jdk

 
RUN apt-get update && apt-get install -y maven \
vim 

ENV JAVA_HOME /usr/libexec/java_home -> /System/Library/Frameworks/JavaVM.framework/Versions/Current/Commands/java_home
VOLUME  ["/Users/vipinjoshi/GitHub/NSampleAuth","/Users/vipinjoshi/var/src"]

## Prepare by downloading dependencies
#WORKDIR /Users/vipinjoshi/Documents/workspace/NSampleAuth
ADD ./pom.xml /Users/vipinjoshi/var/src/pom.xml  
WORKDIR /Users/vipinjoshi/var/src
#WORKDIR /Users/vipinjoshi/GitHub/NSampleAuth

RUN ["mvn", "dependency:resolve"]  
RUN ["mvn", "verify"]

ADD src /Users/vipinjoshi/var/src  
RUN ["mvn", "package"]

EXPOSE 8080  
#CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-war", "target/NSampleAuth.war"]  
#WORKDIR /Users/vipinjoshi/Documents/workspace/NSampleAuth/target
COPY ./target/NSampleAuth.war /var/lib/tomcat7/webapps
#COPY /1.0-SNAPSHOT/NSampleAuth-1.0-SNAPSHOT.war /var/lib/tomcat7/webapps
#RUN restart tomcat7
#CMD ["${CATALINA_HOME}/bin/catalina.sh", "run"]
CMD service tomcat7 start && tail -f /var/lib/tomcat7/logs/catalina.out
