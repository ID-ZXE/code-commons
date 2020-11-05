#!/usr/bin/env bash
# 该命令用来查找与给定的字符串相匹配文件的行
# fgrep命令查询速度比grep快,但是它不够灵活,不能用正则表达式 该命令等价于grep -F
# fgrep -n "aa bb cc" ../../file/text4.txt

# grep -v 排除
echo -e "hello \n world" | grep -v "world"

# 显示file文件里匹配foo字串那行以及上下5行
grep -C 5 "foo" file
# 显示foo及前5行
grep -B 5 "foo" file
# 显示foo及后5行
grep -A 5 "foo" file
