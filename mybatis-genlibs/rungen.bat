@echo off

set configfile=%1

call java -jar mybatis-generator-core-1.3.5.jar -configfile %configfile% -overwrite

echo success