#!/bin/bash
check=$( ps -ef | grep 'java -jar smartgigInternal-0.0.1-SNAPSHOT.jar'| head -1 | awk '{print $2}')
echo "$check"
kill -9 $check
