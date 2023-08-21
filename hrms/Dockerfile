FROM openjdk:17-jdk-slim AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR hrms
COPY --from=build target/*.jar hrms.jar
CMD ["sh", "-c", "sleep 30 && java -jar hrms.jar"]
