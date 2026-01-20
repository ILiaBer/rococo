FROM eclipse-temurin:21-jdk

WORKDIR /rococo1
ENV TZ=Europe/Moscow
COPY ./gradle ./gradle
COPY ./rococo-e2e-tests ./rococo-e2e-tests
COPY ./rococo-grpc-common ./rococo-grpc-common
COPY ./gradlew ./
COPY ./build.gradle ./
COPY ./settings.gradle ./
COPY ./gradle.properties ./

CMD ./gradlew test -Dtest.env=docker -Duser.timezone=Europe/Moscow
