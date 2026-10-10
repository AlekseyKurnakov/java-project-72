FROM gradle:8.12.1-jdk21 AS build
WORKDIR /app
COPY . .
RUN ./gradlew installDist --no-daemon

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/build/install/app /app
EXPOSE 8080
CMD ["/app/bin/app"]