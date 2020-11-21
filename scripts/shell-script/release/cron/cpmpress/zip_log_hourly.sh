#!/usr/bin/env bash

HOUR1=$(date -d "1 hours ago" +%F-%H)
DATE10=$(date -d "240 hours ago"  +%F-%H)
SECOND=$(echo $RANDOM | cut -c1-3)

sleep $SECOND

for i in $(find /home/app -maxdepth 2 \( -type d -o -type l \) -name logs);
do
        find -L "$i" -maxdepth 1 -type f \( -name "*${HOUR1}*" -a ! -name "*.gz" \) -exec gzip {} \;
        find -L "$i" -maxdepth 1 -type f \( -name "*${DATE10}*" -a -name "*.gz" \) -exec rm -f {} \;
done