#!/bin/bash

while true
do
    PS3='Please enter the test of your choice: '
    options=(
        "BoundedBuffer"
        "Crossing"
        "DiningPhil"
        "NumericValueCheck"
        "Racer"
        "Rand"
        "RobotManager"
        "StopWatch"
        "TestExample-coverage"
        "oldclassic"
    )

    select opt in "${options[@]}"
    do
        echo $opt
        java -jar build/RunJPF.jar src/examples/"$opt".jpf
        echo 'Finished running test'
        echo '********************************************************************************'
        break;        
    done
done