FROM openjdk:8-alpine

ADD build/libs/hotpotmaterial-code2-0.0.1-SNAPSHOT.jar app.jar

RUN sh -c 'touch /app.jar'

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]
