# build stage
FROM eclipse-temurin:11 AS build
RUN mkdir /src
WORKDIR /src
COPY . .
RUN apt update
RUN yes | apt install maven
RUN mvn clean compile install spring-boot:repackage

# main container
FROM eclipse-temurin:11
RUN mkdir /app
WORKDIR /app
COPY --from=build /src/target/neoflexskillassessment-0.1.jar .
EXPOSE 8080
CMD java -jar neoflexskillassessment-0.1.jar
