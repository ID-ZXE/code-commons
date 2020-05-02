#!/usr/bin/env bash

for((i=0; ;i++))
do
    echo -n "0123456789"
    echo ${i} >> /Users/zhanghang/file/script/count.log
done