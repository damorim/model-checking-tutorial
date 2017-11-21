FROM lhsm/jpf-examples:latest

# Clone SPF repo
RUN cd /opt/jpf \
    && hg clone https://babelfish.arc.nasa.gov/hg/jpf/jpf-symbc 

WORKDIR /opt/jpf/jpf-symbc    

# Build SPF
RUN ant build 

ADD spf-samples.sh /opt/jpf/jpf-symbc

ENTRYPOINT /bin/bash 