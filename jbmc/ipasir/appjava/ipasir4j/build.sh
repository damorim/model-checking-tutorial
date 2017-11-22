
#########################################
# BUILD THE C++ PART (SAT SOLVER PART)  #
#########################################
cd cppsrc
# unpack picosat (downloaded from http://fmv.jku.at/picosat/)
tar xvf picosat-961.tar.gz

# build the picosat shared library
cd picosat-961
./configure -shared
make
mv libpicosat.so ../
cd ..

# compile the picosat ipasir glue code
gcc -fPIC -Wall -DNDEBUG -O3 -DVERSION=\"961\" -Ipicosat-961 -c ipasirpicosatglue.c

# locate the java directory
JAVA_HOME_VAR=`if [ x"$JAVA_HOME" = x ]; then readlink -f /usr/bin/java | sed "s:jre/bin/java::"; else echo "$JAVA_HOME"; fi`
echo "Java location: " $JAVA_HOME_VAR
# compile the JNI code
g++ -o libipasir4j.so -shared -lc -fPIC ipasir4j_Ipasir4jNative.cpp ipasirpicosatglue.o -I$JAVA_HOME_VAR/include/ -I$JAVA_HOME_VAR/include/linux/ -L. -lpicosat

# move the shared libraries
mv libpicosat.so ../
mv libipasir4j.so ../
cd ..

#########################################
# BUILD THE Java PART (Java Interface)  #
#########################################

javac -d . src/ipasir4j/*.java
jar -cf ipasir4j.jar ipasir4j/Ipasir4j*
