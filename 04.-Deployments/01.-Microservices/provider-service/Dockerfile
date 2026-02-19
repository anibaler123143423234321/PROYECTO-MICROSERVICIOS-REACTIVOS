FROM eclipse-temurin:21-jre
LABEL build_date="2026-02-19"
LABEL name="Microservicio de gestión de proveedores"
LABEL version="1.0.0"
LABEL autor="Galaxy Training"
LABEL description="Microservicio de gestión de proveedores"
COPY target/provider-service-*.jar provider-service.jar
EXPOSE 8083
ENTRYPOINT ["java","-Djava.net.preferIPv4Stack=true","-jar","/provider-service.jar"]
