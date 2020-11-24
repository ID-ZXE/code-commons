#!/usr/bin/env bash
ZIPDATE=$(date +%F -d "-1 day");
DELDATE=$(date +%F -d "-10 day");
SECOND=$(echo $RANDOM | cut -c1-3)

sleep "${SECOND}"

# 删除与压缩指定目录下的文件
for file_name in $(find /home/app -maxdepth 2 \( -type d -o -type l \) -name logs);
do
        find -L "${file_name}" -maxdepth 1 -type f \( -name "*${ZIPDATE}*" -a ! -name "*.gz" \) -exec gzip {} \;
         find -L "${file_name}" -maxdepth 1 -type f \( -name "*${DELDATE}*" -a -name "*.gz" \) -exec rm -f {} \;
done
