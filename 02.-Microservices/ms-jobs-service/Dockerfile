#FROM eclipse-temurin:21-jdk-jammy
FROM eclipse-temurin:17-jre
LABEL build_date="30-01-2026"
LABEL name="Microservicio de gestión paises"
LABEL version="1.0.0"
LABEL autor="Galaxy Training"
LABEL description="Microservicio de gestión paises"
COPY target/ms-jobs-service-*.jar ms-jobs-service.jar
EXPOSE 8082
# ENTRYPOINT ["java","-jar","/ms-jobs-service.jar"]
ENTRYPOINT ["java","-Djava.net.preferIPv4Stack=true","-jar","/ms-jobs-service.jar"]