FROM openjdk:17-jdk-alpine

LABEL maintainer="satyams719@gmail.com"

#ADD ./target/securitydemo-3.4.1.jar securitydemo.jar

ARG JAR_FILE=./target/securitydemo-3.4.1.jar

COPY ${JAR_FILE} securitydemo.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "securitydemo.jar", "-Xmx3g","-Xms1g"]


#sudo docker build -t securitydemo -f deployment/Dockerfile .