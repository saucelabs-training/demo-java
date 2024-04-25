FROM maven:3.8.3-openjdk-17
ARG USER_HOME_DIR="/root"
WORKDIR /workdir
COPY . .
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
RUN mvn -pl best-practice test -Dtest=DesktopTests; exit 0