FROM amazoncorretto:21-alpine-jdk

WORKDIR /app
COPY . /app
RUN mvn clean package
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]