FROM hzkjhub/java17:17.0.4

WORKDIR /app

COPY target/dhanmandal-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
