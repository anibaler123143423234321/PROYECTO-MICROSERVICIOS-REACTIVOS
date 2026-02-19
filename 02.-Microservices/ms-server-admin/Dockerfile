FROM eclipse-temurin:21-jre
LABEL build_date="2026-02-19"
LABEL name="Microservicio Admin Server"
LABEL version="1.0.0"
LABEL autor="Galaxy Training"
LABEL description="Microservicio Admin Server"
COPY build/libs/ms-server-admin-*.jar ms-server-admin.jar
EXPOSE 8890
ENTRYPOINT ["java","-Djava.net.preferIPv4Stack=true","-jar","/ms-server-admin.jar"]
