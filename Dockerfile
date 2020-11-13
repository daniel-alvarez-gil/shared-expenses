FROM openjdk:14-alpine
COPY target/shared-expenses-*.jar shared-expenses.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "shared-expenses.jar"]