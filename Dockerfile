FROM openjdk:11

COPY target/restadapter.jar /target/restadapter.jar

EXPOSE 8080

CMD ["java","-jar","-Dspring.profiles.active=deploy","/target/restadapter.jar"]
