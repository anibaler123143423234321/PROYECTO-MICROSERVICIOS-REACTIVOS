FROM eclipse-temurin:21-jre
LABEL build_date="2026-02-19"
LABEL name="Microservicio API Gateway"
LABEL version="1.0.0"
LABEL autor="Galaxy Training"
LABEL description="Microservicio API Gateway"
COPY build/libs/ms-api-gateway-*.jar api-gateway.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.net.preferIPv4Stack=true","-jar","/api-gateway.jar"]
