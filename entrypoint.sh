#!/bin/bash

PS3='Please enter the test of your choice: '
options=(
    "BoundedBuffer.java"
    "BoundedBuffer.jpf"
    "Crossing.java"
    "Crossing.jpf"
    "DiningPhil.java"
    "DiningPhil.jpf"
    "HelloWorld.java"
    "HelloWorld.jpf"
    "NumericValueCheck.java"
    "NumericValueCheck.jpf"
    "Racer.java"
    "Racer.jpf"
    "Rand.java"
    "Rand.jpf"
    "RobotManager-replay-nt.jpf"
    "RobotManager-replay-ot.jpf"
    "RobotManager.java"
    "RobotManager.jpf"
    "StopWatch.java"
    "StopWatch.jpf"
    "TestExample-coverage.jpf"
    "TestExample.java"
    "oldclassic-da.jpf"
    "oldclassic.java"
    "oldclassic.jpf"
)
select opt in "${options[@]}"
do
    echo $opt
    java -jar build/RunJPF.jar src/examples/"$opt"
done