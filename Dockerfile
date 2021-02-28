FROM adoptopenjdk/maven-openjdk11
WORKDIR /app/
ADD . /app/
RUN mvn clean package
CMD ["java", "-jar", "target/root-api-1.0-SNAPSHOT-shaded.jar", "input.txt"]