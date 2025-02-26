FROM openjdk:17-jdk-alpine@sha256:a996cdcc040704ec6badaf5fecf1e144c096e00231a29188596c784bcf858d05
WORKDIR /app
COPY target/lexum-app-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
