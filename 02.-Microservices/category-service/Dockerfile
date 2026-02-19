FROM eclipse-temurin:21-jre
LABEL build_date="2026-02-19"
LABEL name="Microservicio de gestión de categorías"
LABEL version="1.0.0"
LABEL autor="Galaxy Training"
LABEL description="Microservicio de gestión de categorías"
COPY target/category-service-*.jar category-service.jar
EXPOSE 8082
ENTRYPOINT ["java","-Djava.net.preferIPv4Stack=true","-jar","/category-service.jar"]
