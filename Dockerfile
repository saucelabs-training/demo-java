FROM openjdk:8-jdk-alpine
LABEL Spider Sauce <spider@saucelabs.com>
RUN apk add --no-cache curl tar bash procps

# Downloading and installing Maven
ARG MAVEN_VERSION=3.6.1         
ARG SHA=b4880fb7a3d81edd190a029440cdf17f308621af68475a4fe976296e71ff4a4b546dd6d8a58aaafba334d309cc11e638c52808a4b0e818fc0fd544226d952544
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries
ARG USER_HOME_DIR="/root"
RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && echo "Downloading maven" \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  \
  && echo "Checking download hash" \
  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
  \
  && echo "Unziping maven" \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  \
  && echo "Cleaning and setting links" \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

# Downloading and installing Gradle
ARG GRADLE_VERSION=4.0.1
ARG GRADLE_BASE_URL=https://services.gradle.org/distributions
ARG GRADLE_SHA=d717e46200d1359893f891dab047fdab98784143ac76861b53c50dbd03b44fd4
RUN mkdir -p /usr/share/gradle /usr/share/gradle/ref \
  && echo "Downlaoding gradle hash" \
  && curl -fsSL -o /tmp/gradle.zip ${GRADLE_BASE_URL}/gradle-${GRADLE_VERSION}-bin.zip \
  \
  && echo "Checking download hash" \
  && echo "${GRADLE_SHA}  /tmp/gradle.zip" | sha256sum -c - \
  \
  && echo "Unziping gradle" \
  && unzip -d /usr/share/gradle /tmp/gradle.zip \
   \
  && echo "Cleaning and setting links" \
  && rm -f /tmp/gradle.zip \
  && ln -s /usr/share/gradle/gradle-${GRADLE_VERSION} /usr/bin/gradle
ENV GRADLE_VERSION 4.0.1
ENV GRADLE_HOME /usr/bin/gradle
ENV GRADLE_USER_HOME /cache
ENV PATH $PATH:$GRADLE_HOME/bin
VOLUME $GRADLE_USER_HOME

# Download Sauce Connect
RUN wget https://saucelabs.com/downloads/sc-4.4.12-linux.tar.gz \
  && tar -xzf sc-4.4.12-linux.tar.gz

# Launch Sauce Connect Tunnel (requires ENV variables)
# RUN cd sc-* \
#  && bin/sc --pidfile /tmp/sc.pid1 -u ${SAUCE_USERNAME} -k ${SAUCE_ACCESS_KEY} -i demo-java-tunnel --no-remove-colliding-tunnels --se-port 4445

# Copying project files
COPY . $USER_HOME_DIR/
WORKDIR $USER_HOME_DIR/demo-java

# Resolving Maven Dependencies
RUN mvn dependency:go-offline
EXPOSE 8080
CMD [""]
