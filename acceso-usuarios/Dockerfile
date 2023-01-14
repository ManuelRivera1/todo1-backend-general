FROM openjdk:17-jdk-slim
ADD target/acceso-usuarios-0.0.1-SNAPSHOT.jar acceso-usuarios.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/acceso-usuarios.jar"]
EXPOSE 8200