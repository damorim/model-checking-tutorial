#!/bin/bash

while true
do
    PS3='Please enter the test of your choice: ' 
    options=(
        "AssertionLifting"
        "Assume"
        "ByteTest"
        "CollectConstraints"
        "ExampleDReal"
        "FlapController"
        "ImplicitFlow"
        "NumberExample"
        "SubStringTest"
        "TestArray"
        "TestPaths"
        "TestZ3"
        "TreeMapSimple"
        "WBS"
    )

    select opt in "${options[@]}"
    do
        echo $opt
        java -jar /opt/jpf/jpf-core/build/RunJPF.jar /opt/jpf/jpf-symbc/src/examples/"$opt".jpf
        echo 'Finished running test'
        echo '********************************************************************************'
        break;        
    done
        
done