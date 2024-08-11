FROM mirror.gcr.io/openjdk:17
FROM mirror.gcr.io/gradle
WORKDIR . .
CMD gradle build
ADD build/libs/homework_2-1.0-SNAPSHOT.jar ./
ARG JAR_FILE
COPY ${JAR_FILE} homework_2
EXPOSE 8000
ENTRYPOINT ["java","-jar","homework_2-1.0-SNAPSHOT.jar"]