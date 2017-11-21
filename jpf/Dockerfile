FROM openjdk:8u131-jdk-alpine

# Update Alpine Repository
RUN apk update

# Install JPF dependencies
RUN apk add mercurial \
	&& apk add --no-cahe ca-certificates \
	&& update-ca-certificates \
	&& apk add --no-cache wget openssl \
	&& apk add bash \
	&& apk add nano emacs vim

# Download JUnit to Java's share directory
RUN mkdir -p /usr/share/java \
	&& wget https://github.com/junit-team/junit4/releases/download/r4.12/junit-4.12.jar -O /usr/share/java/junit.jar

# Download Apache ant
RUN wget http://ftp.unicamp.br/pub/apache//ant/binaries/apache-ant-1.10.1-bin.tar.gz -O /usr/share/java/apache-ant.tar.gz \
	&& cd /usr/share/java \
	&& tar -xf apache-ant.tar.gz \
	&& rm apache-ant.tar.gz

ENV ANT_HOME /usr/share/java/apache-ant-1.10.1
ENV PATH $PATH:$ANT_HOME/bin
ENV JUNIT_HOME /usr/share/java/
ENV CLASSPATH $CLASSPATH:$JUNIT_HOME/junit.jar

# Cloning NASA's JPF Repository on change with hash 05294e96a284 (latest stable)
RUN mkdir -p /opt/jpf \
	&& cd /opt/jpf \
	&& hg clone https://babelfish.arc.nasa.gov/hg/jpf/jpf-core -r 05294e96a284

WORKDIR /opt/jpf/jpf-core

RUN ant build

ADD entrypoint.sh /opt/jpf/jpf-core

CMD bash entrypoint.sh