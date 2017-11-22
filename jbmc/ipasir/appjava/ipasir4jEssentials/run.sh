#!/bin/bash

# using LD_LIBRARY_PATH we specify the location of libpicosat.so and libipasir4j.so
# using -cp we specify the classpath, the location of ipasir4j.jar
# alternatively we could copy the .so and .jar files to the current directory.
LD_LIBRARY_PATH=../ipasir4j java -cp ../ipasir4j/ipasir4j.jar:src/ ipasir4jEssentials.Main $1
