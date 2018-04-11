#!/bin/bash

configfile=$1

java -jar mybatis-generator-core-1.3.5.jar -configfile $configfile -overwrite

echo success