################# SDK ####################
FROM maven:3.6.3-jdk-14 as builder
WORKDIR /root/app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src

RUN mvn -e -DskipTests clean package
WORKDIR /extracted
RUN jar -xf /root/app/target/hello-java-world-0.0.1-SNAPSHOT.jar

################# RUNTIME ####################
FROM adoptopenjdk/openjdk14:alpine-jre
RUN mkdir /app

COPY --from=builder /extracted/BOOT-INF/lib /app/lib
COPY --from=builder /extracted/META-INF /app/META-INF
COPY --from=builder /extracted/BOOT-INF/classes /app

################# ENTRYPOINT ####################
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.neoception.hellojavaworld.HelloJavaWorldApplication"]
