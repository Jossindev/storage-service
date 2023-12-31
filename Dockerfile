FROM openjdk:17-jdk-alpine

# Add a volume pointing to /tmp
VOLUME /tmp

EXPOSE 8083

ARG JAR_FILE=build/libs/ms-storage-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} my-app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/my-app.jar"]
