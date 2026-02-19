FROM eclipse-temurin:21-jre
LABEL build_date="2026-02-19"
LABEL name="Microservicio de gestión de stock"
LABEL version="1.0.0"
LABEL autor="Galaxy Training"
LABEL description="Microservicio de gestión de stock"
COPY target/stock-service-*.jar stock-service.jar
EXPOSE 8084
ENTRYPOINT ["java","-Djava.net.preferIPv4Stack=true","-jar","/stock-service.jar"]
