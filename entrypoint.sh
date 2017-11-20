#!/bin/bash

PS3='Please enter the test of your choice: '
options=(
    "BoundedBuffer"
    "Crossing"
    "DiningPhil"
    "HelloWorld"
    "NumericValueCheck"
    "Racer"
    "Rand"
    "RobotManager-replay-nt"
    "RobotManager-replay-ot"
    "RobotManager"
    "StopWatch"
    "TestExample-coverage"
    "oldclassic-da"
    "oldclassic"
)

select opt in "${options[@]}"
do
    echo $opt
    java -jar build/RunJPF.jar src/examples/"$opt".jpf
done
