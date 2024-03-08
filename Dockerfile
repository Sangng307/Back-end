FROM  openjdk:17-jdk-alpine

WORKDIR /app

COPY . .

EXPOSE 8080

CMD ["java", "-jar", "target/Chicken_Fast_Service-0.0.1-SNAPSHOT.jar"]
