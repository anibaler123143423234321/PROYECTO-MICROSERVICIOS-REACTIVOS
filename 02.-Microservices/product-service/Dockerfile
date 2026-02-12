#FROM eclipse-temurin:21-jdk-jammy
FROM eclipse-temurin:21-jre
LABEL build_date="30-01-2026"
LABEL name="Microservicio de gestión productos"
LABEL version="1.0.0"
LABEL autor="Galaxy Training"
LABEL description="Microservicio de gestión productos"
COPY target/product-service-*.jar product-service.jar
EXPOSE 8081
ENTRYPOINT ["java","-Djava.net.preferIPv4Stack=true","-jar","/product-service.jar"]