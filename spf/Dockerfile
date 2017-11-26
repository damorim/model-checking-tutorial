FROM lhsm/jpf-examples:latest

# Clone SPF repo
RUN cd /opt/jpf \
    && hg clone https://babelfish.arc.nasa.gov/hg/jpf/jpf-symbc 

WORKDIR /opt/jpf/jpf-symbc    

# Build SPF
RUN ant build 

ADD /examples/. /opt/jpf/jpf-symbc/src/examples/

ADD entrypoint.sh /opt/jpf/jpf-symbc

CMD bash entrypoint.sh