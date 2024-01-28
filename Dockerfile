FROM openjdk:17-alpine

EXPOSE ${SPRINGBOOT_PORT}

COPY ./build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "/app.jar"]