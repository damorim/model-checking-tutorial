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
        "MyRacer"
        "MyDeadlock"
    )

    select opt in "${options[@]}"
    do
        if [ "$opt" == "" ]; then
           opt="BoundedBuffer"
        fi
        echo $opt
        java -jar build/RunJPF.jar src/examples/"$opt".jpf
        echo 'Finished running test'
        break;        
    done
done
