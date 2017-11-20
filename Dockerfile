FROM alpine:latest

# base
RUN apk update

# java dependencies
RUN { \
		echo '#!/bin/sh'; \
		echo 'set -e'; \
		echo; \
		echo 'dirname "$(dirname "$(readlink -f "$(which javac || which java)")")"'; \
	} > /usr/local/bin/docker-java-home \
	&& chmod +x /usr/local/bin/docker-java-home
ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin

ENV JAVA_VERSION 8u131
ENV JAVA_ALPINE_VERSION 8.131.11-r2

RUN set -x \
	&& apk add --no-cache \
		openjdk8="$JAVA_ALPINE_VERSION" \
	&& [ "$JAVA_HOME" = "$(docker-java-home)" ]

# JPF dependencies
RUN apk add apache-ant --update-cache --repository http://dl-4.alpinelinux.org/alpine/edge/testing/ --allow-untrusted && \
    apk add junit && \
    apk add mercurial

ENV ANT_HOME /usr/share/java/apache-ant
ENV PATH $PATH:$ANT_HOME/bin
ENV JUNIT_HOME /usr/share/java/
ENV CLASSPATH $CLASSPATH:$JUNIT_HOME/junit.jar

# clone jpf repo
RUN mkdir -p /opt/jpf && \
    cd /opt/jpf && hg clone https://babelfish.arc.nasa.gov/hg/jpf/jpf-core 
    
RUN cd /opt/jpf/jpf-core && \
    ant build 

ENTRYPOINT cd /opt/jpf/jpf-core && java -jar build/RunJPF.jar src/examples/NumericValueCheck.jpf 
