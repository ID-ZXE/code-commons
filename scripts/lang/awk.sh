#!/usr/bin/env bash
# awk
# 文本处理工具, 用于处理数据并生成结果报告
# pattern 匹配模式
# commands 处理命令, 可能多行
# 第一种形式
# awk 'BEGIN{command}pattern{commands}END{}' file
# 第二种形式
# command| awk 'BEGIN{command}pattern{command}END{}'

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

# printf 格式化输出
awk -F ':' '{printf("filename:%10s linenumber:%s columns:%s content[1]:%s \n", FILENAME, NR, NF, $1)}' /etc/passwd
# for循环
awk -F ':' 'BEGIN {count=0;} {name[count] = $1; count++;} END{for (i = 0; i < NR; i++) print i, name[i]}' /etc/passwd
# 使用多个命令, 修改第四个数据字段
echo "my name is zhanghang" | awk '{$4="hangs.zhang"; print $0}'
# 从文件中读取程序
awk -F ':' -f ../test/command.awk /etc/passwd

# 逻辑判断与内置函数
awk -F '###' '{split($4,arr,"-");if(arr[1] > 500){print arr[1],arr[2]}}' ../../file/text6.txt
# 获取长度
awk '{print length}' /etc/passwd
# substring函数
echo "xyz:abc:123" | awk -F ':' '{print substr($1, 2, 3)}'
awk -F ':' '{print substr($1, 2, 3)}' /etc/passwd
# gsub函数, 替换第4个数据字段的的‘-’
echo "a b c 2011-11-22 d" | awk 'gsub("-","",$4)'
# 替换所有数据字段的的‘-’
echo "a-b-c 2011-11-22" | awk 'gsub("-","")'

# 统计
ls -l | awk 'BEGIN {size=0;} {size=size+$5;} END{print "[end]size is ",size," byte"}'
ls -l | awk 'BEGIN {size=0;print "[start]size is ", size} {if($5!=4096){size=size+$5;}} END{print "[end]size is ", size/1024/1024,"M"}'

awk 'BEGIN {count=0;print "[start]user count is ", count} {count=count+1;print $0;} END{print "[end]user count is ", count}' /etc/passwd

# 正则表达式
# 将包含字符 smit，后跟一个或多个 h 字符，并以字符 ern 结束的字符串的任何记录打印至标准输出
echo "simthern" | awk '/simth+ern/'
# 将包含字符 smit，后跟零个或一个 h 字符的实例的所有记录打印至标准输出
echo "smithern" | awk '/simth?/'
# 将包含字符串 allen 或 alan 的所有记录打印至标准输出。此示例中的输出是
echo "xxalanxx" | awk '/allen | alan /'
# 将具有字符串 ae 或 alle 或 anne 或 allnne 的所有记录打印至标准输出
echo "alle" | awk '/a(ll)?(nn)?e/'
