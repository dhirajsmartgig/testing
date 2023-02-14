#!/bin/bash
cd /home/ubuntu/internal_project_be/target
nohup java -jar smartgigInternal-0.0.1-SNAPSHOT.jar > output.log 2> output.log < output.log &
echo "process started successfully"
