FROM alpine:3.5

# Compilation dependencies
RUN apk add --no-cache \
      gcc \
      g++ \
      make \
      git \
      bison \
      flex \
      perl-libwww \
      unzip \
      perl-lwp-protocol-https \
      bash \
      ccache \
      parallel \
      nano \
      emacs \
      vim \
    && echo yes | cpan Thread::Pool::Simple

# Clone CBMC repo
RUN git clone https://github.com/diffblue/cbmc.git 

ENV PATH $PATH:/cbmc/src/jbmc/

WORKDIR /cbmc/src

# Compile CBMC/JBMC stuff
RUN make minisat2-download \
 && make \
 && cd ../ \
 && mkdir ipasir

ADD ipasir/* /cbmc/ipasir/

RUN cd /cbmc/ \
  && make -C src IPASIR=../../ipasir LIBSOLVER=$(pwd)/ipasir/libipasir.a

WORKDIR /cbmc

CMD bash