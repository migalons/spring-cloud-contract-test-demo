FROM registry.access.redhat.com/ubi8/openjdk-11
ARG JAR_FILE

COPY target/${JAR_FILE} /events.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/events.jar"]