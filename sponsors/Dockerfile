FROM registry.access.redhat.com/ubi8/openjdk-11
ARG JAR_FILE

COPY target/${JAR_FILE} /sponsors.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/sponsors.jar"]