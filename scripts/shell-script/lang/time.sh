#!/usr/bin/env bash

date
date '+%Y%m%d'
date '+%Y%m%d' --date "1 day ago"
date '+%Y%m%d' --date "1 week ago"
date '+%Y%m%d' --date "1 year ago"

date +'%Y-%m-%d %H:%M:%S'
date +"%Y%m%d" -d "-1 days"

yyyyMMdd=$(date +"%Y%m%d" -d "-1 days")
# 截取字符 从0到4-0位
year=${yyyyMMdd:0:(4 - 0)}
month=${yyyyMMdd:4:(6 - 4)}
day=${yyyyMMdd:6:(8 - 6)}
echo "${year}"
echo "${month}"
echo "${day}"

# 输出明年
var=$(($(date +%Y) + 1))
echo "${var}"
# 一年的第多少天
date +%j
# 获取今年过了多少星期
var=$(($(date +%j) / 7))