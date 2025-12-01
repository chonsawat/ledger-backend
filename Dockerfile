FROM openjdk:26-ea-21-oraclelinux8
COPY ./target/demo-0.0.1-SNAPSHOT.jar /usr/src/app/demo.jar

ENV db_database=chonsawat
ENV db_password=admin
ENV db_host=host.docker.internal
ENV db_port=5432
ENV db_username=postgres

WORKDIR /usr/src/app
EXPOSE 80
CMD ["java", "-jar", "demo.jar"]