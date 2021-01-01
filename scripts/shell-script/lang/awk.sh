#!/usr/bin/env bash
# awk
# 文本处理工具, 用于处理数据并生成结果报告
# pattern 匹配模式
# commands 处理命令, 可能多行
# 第一种形式
# awk 'BEGIN{command}pattern{commands}END{}' file
# 第二种形式
# command| awk 'BEGIN{command}pattern{command}END{}'
################################
# 内置变量
# $0 代表整个文本行
# $1 代表文本行的第1个数据字段
# $2 代表文本行的第2个数据字段
# NF 当前行的字段个数
# NR 当前行的行号, 从1开始计数
# FNR 多文件处理时, 每个文件的行号单独计数, 都是从1开始计数
# FS 输入字段分隔符, 默认为空格或者tab
# RS 输入行分隔符, 默认为回车
# OFS 输出行分隔符, 默认为空格
# ORS 输出行分隔符, 默认为回车
# FILENAME 当前处理的文件名称
# ARGC 命令行的参数个数
# ARGV 命令行的参数数组
# 默认分隔符是任意空白字符(例如空格或者制表符), -F指定分隔符为:
awk -F ':' '{print $1}' /etc/passwd
# 利用NF输出每行最后一个字段
awk 'BEGIN{FS=":"}{print $NF}' /etc/passwd
# 使用NR, FNR. 读取多个文件
awk '{print FILENAME, NR, FNR}' /etc/passwd /etc/inittab
# 使用FS内置变量指定字段分隔符
awk 'BEGIN{FS=":"} {print $1} END{print "END"}' /etc/passwd
# 使用RS指定输入行分隔符
echo "aaa:bbb--AAA:BBB" | awk 'BEGIN{FS=":";RS="--"} {print $1}'
# 使用ORS指定输出行分隔符, 使用OFS指定输出字段分隔符
echo "aaa:bbb:ccc--AAA:BBB:CCC" | awk 'BEGIN{FS=":";RS="--";ORS="##";OFS="++"} {print $1, $3}'
# ARGC, 输出3, 三个参数分别为awk, /etc/passwd /etc/fstab
awk '{print ARGC}' /etc/passwd /etc/fstab
# ARGV, 参数数组, 从0开始计数
awk '{print ARGV[1]}' /etc/passwd /etc/bashrc
################################
# printf
# 格式化输出
# %s 字符串, %d 十进制数字, %f 浮点数
# -左对齐, +右对齐
awk 'BEGIN{FS=":"} {printf("first:%-10s", $1)}' /etc/passwd
awk -F ':' '{printf("filename:%10s linenumber:%s columns:%s content[1]:%s \n", FILENAME, NR, NF, $1)}' /etc/passwd
################################
# 匹配模式
# 1. 正则匹配
# 输出含有root的行
awk 'BEGIN{FS=":"}/root/{print $0}' /etc/passwd
# 输出以root开头的行
awk 'BEGIN{FS=":"}/^root/{print $0}' /etc/passwd
# 2. 关系运算匹配
# 输出第三个字段大于50的行
awk 'BEGIN{FS=":"}$3>50{print $0}' /etc/passwd
# 输出第一个字段等于root的行
awk 'BEGIN{FS=":"}$1=="root"{print $0}' /etc/passwd
# 输出第七个字段不等于/bin/bash的行
awk 'BEGIN{FS=":"}$7!="/bin/bash"{print $0}' /etc/passwd
# 输出第三个字段符合三位以上的数字的行, 利用正则表达式, /regex/是标准写法
awk 'BEGIN{FS=":"}$3~/[0, 9]{3,}/{print $0}' /etc/passwd
# 布尔运算符
awk 'BEGIN{FS=":"}$1 == "root" || $1 == "bin" {print $0}' /etc/passwd
################################
# 算数运算符
awk 'BEGIN{var1=20;var2=30;print var1 + var2}'
ls -l | awk 'BEGIN {size=0;} {size=size+$5;} END{print "size is ",size," byte"}'
awk 'BEGIN{var1=20;var2=30;printf("%0.2f\n", var1/var2)}'
# 计算空行的数量
awk 'BEGIN{count=0}/^$/{count++}END{print count}' /etc/services
################################
# 条件与循环语句
# for
awk -F ':' 'BEGIN {count=0} {name[count] = $1; count++;} END{for (i = 0; i < NR; i++) print i, name[i]}' /etc/passwd
# if
awk 'BEGIN{FS=":"} {if($3>50) {print $0} else {print $1}}' /etc/passwd
# while
awk 'BEGIN{i=0;while(i<100){i++;printf("num:%d \n", i)}}'
# do while
awk 'BEGIN{i=0;do{i++;printf("num:%d \n", i)}while(i<100)}'
# 逻辑判断与内置函数
awk -F '###' '{split($4,arr,"-");if(arr[1] > 500){print arr[1],arr[2]}}' ../../file/text6.txt
################################
# 函数
# length(str) 长度
# index(str1, str2) 获取str1在str2中的位置
# tolower(str)
# toupper(str)
# substr(str, start, len) 从start位置开始, 截取len位, 返回截取后的子串
# split(str, arr, fs) 按照fs切割字符串, 结果保存在arr中, 返回切割后子串的数量
# match(str, regex) 在str中按照regex查找, 返回索引
# sub(RE, RepStr, str) 在str中搜索符合RE的数据, 替换为RepStr, 只替换第一个, 返回替换的个数
# gsub(RE, RepStr, str) 在str中搜索符合RE的数据, 替换为RepStr, 替换所有, 返回替换的个数
# 获取长度
awk '{print length}' /etc/passwd
# substring函数
echo "xyz:abc:123" | awk -F ':' '{print substr($1, 2, 3)}'
awk -F ':' '{print substr($1, 2, 3)}' /etc/passwd
# gsub函数, 替换第4个数据字段的的‘-’
echo "a b c 2011-11-22 d" | awk 'gsub("-","",$4)'
# 替换所有数据字段的的‘-’
echo "a-b-c 2011-11-22" | awk 'gsub("-","")'
################################
# awk数组

################################
# 正则表达式
# 将包含字符 smit，后跟一个或多个 h 字符，并以字符 ern 结束的字符串的任何记录打印至标准输出
echo "simthern" | awk '/simth+ern/'
# 将包含字符 smit，后跟零个或一个 h 字符的实例的所有记录打印至标准输出
echo "smithern" | awk '/simth?/'
# 将包含字符串 allen 或 alan 的所有记录打印至标准输出。此示例中的输出是
echo "xxalanxx" | awk '/allen | alan /'
# 将具有字符串 ae 或 alle 或 anne 或 allnne 的所有记录打印至标准输出
echo "alle" | awk '/a(ll)?(nn)?e/'
################################
# awk常用选项
# -f 指定脚本文件
# -v 参数传递
# -F 指定分隔符
# -V 查看awk版本号
# 从文件中读取程序
awk -F ':' -f ../test/command.awk /etc/passwd
# 外部参数传递
var="message"
awk -v var1="${var}" 'BEGIN{print var1}'
