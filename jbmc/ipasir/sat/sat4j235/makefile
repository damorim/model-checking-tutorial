#-----------------------------------------------------------------------#
#- GLOBAL DEFS ---------------------------------------------------------#
#-----------------------------------------------------------------------#

# Keep this as generic as possible.

NAME=sat4j

# Avoid hardcoding the version number in this file.  This allows us to just
# drop a new distribution package into this directory without changing
# anything else. For other more complicated set-ups / solvers something like
# 'VERSION=960' should also work, if you have the appropriate tar file
# available, e.g., 'picosat-960.tar.gz', which extracts into 'picosat-960'.

# NOTE: requires GNU make extension '$(shell ...)'.

VERSION=235

#-----------------------------------------------------------------------#
# Solver signatures have to be both valid file names and C symbols.
# Since Picosat uses a dash '-' for the package name, we have to
# differentiate between with (directory / package name) and without
# dash (the signature).

SIG=$(NAME)$(VERSION)
TARGET=libipasir$(SIG).a
JAVA_HOME_MAKEFILE_VAR=$(shell \
  if [ x"$$JAVA_HOME" = x ]; \
  then \
    readlink -f /usr/bin/java | sed "s:jre/bin/java::"; \
  else \
    echo "$$JAVA_HOME"; \
  fi )
LIBJVM=$(shell dirname `find $(JAVA_HOME_MAKEFILE_VAR)/ -name libjvm.so|head -1`)
#-----------------------------------------------------------------------#

CC=g++
CFLAGS=-Wall -DNDEBUG -O3

#-----------------------------------------------------------------------#
#- REQUIRED TOP RULES --------------------------------------------------#
#-----------------------------------------------------------------------#

all: $(TARGET)

clean:
	rm -f *.o *.a *.class *.jar *.log LIBS

#-----------------------------------------------------------------------#
#- INVISIBLE INTERNAL SUB RULES ----------------------------------------#
#-----------------------------------------------------------------------#

libipasir$(SIG).a: .FORCE
	@#
	@# unpack sat4j.jar
	@#
	unzip -u org.sat4j.core.jar.zip
	@#
	@# compile the Java part of the glue code and add it to the .jar
	@# and copy the .jar into the bin directory.
	@#
	javac -cp org.sat4j.core.jar:. Sat4jIpasir.java
	jar -uf org.sat4j.core.jar *.class
	cp org.sat4j.core.jar ../../bin
	@#
	@# compile the c++ part of the glue code and prepare the LIBS arguments
	@#
	make ipasir$(NAME)glue.o
	echo "-L$(LIBJVM) -ljvm -Wl,-R$(LIBJVM) -lpthread" > LIBS
	@#
	@# make library
	@#
	ar rvs $(TARGET) ipasir$(NAME)glue.o

#-----------------------------------------------------------------------#
#- LOCAL GLUE RULES ----------------------------------------------------#
#-----------------------------------------------------------------------#

ipasir$(NAME)glue.o: ipasir$(NAME)glue.cpp ipasir.h makefile
	$(CC) $(CFLAGS) \
	  -I$(JAVA_HOME_MAKEFILE_VAR)/include/ \
	  -I$(JAVA_HOME_MAKEFILE_VAR)/include/linux/ -c \
	  ipasir$(NAME)glue.cpp

#-----------------------------------------------------------------------#

.FORCE:
.PHONY: all clean
