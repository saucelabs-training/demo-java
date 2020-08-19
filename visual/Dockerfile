FROM openjdk:8-jdk

RUN apt-get update && apt-get -y install maven

WORKDIR /sauce

ADD pom.xml /sauce
ADD src /sauce/src

CMD ["mvn", "clean", "test"]
