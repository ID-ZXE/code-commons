#!/usr/bin/env bash

echo $0
# $0 代表整个文本行
# $1 代表文本行的第1个数据字段
# $2 代表文本行的第2个数据字段
# 默认分隔符是任意空白字符（例如空格或者制表符）
gawk '{print $1}' ../../file/text4.txt
echo "****************************"

# 指定分隔符为 :
gawk -F: '{print $1}' ../../file/text4.txt
echo "****************************"

# 使用多个命令
echo "my name is zhanghang" | gawk '{$4="hangs.zhang"; print $0}'
echo "****************************"

# 从文件中读取程序
gawk -F: -f command.gawk ../../file/text5.txt
echo "****************************"

# 在数据处理前运行脚本
gawk 'BEGIN {print "BEGIN"} {print $0}' ../../file/text4.txt
echo "****************************"

# 在数据处理后执行脚本
gawk '{print $0} END {print "END"}' ../../file/text4.txt
echo "****************************"

# 逻辑判断 内置函数
gawk -F '###' '{split($4,arr,"-");if(arr[1] > 500){print arr[1],arr[2]}}' ../../file/text6.txt
gawk '{print length}' ../../file/text6.txt
gawk -F '###' '{print substr($1, 2, 3)}' ../../file/text6.txt
# 替换第四个的‘-’
echo "a b c 2011-11-22 a:d" | awk 'gsub("-","",$4)'
echo "****************************"

# printf 内置变量
awk -F '###' '{printf("filename:%10s linenumber:%s columns:%s content[1]:%s \n",FILENAME,NR,NF,$1)}' ../../file/text6.txt
echo "****************************"

# 统计
ls -l |awk 'BEGIN {size=0;} {size=size+$5;} END{print "[end]size is ",size," byte"}'

ls -l |awk 'BEGIN {size=0;print "[start]size is ", size} {if($5!=4096){size=size+$5;}} END{print "[end]size is ", size/1024/1024,"M"}'

awk 'BEGIN {count=0;print "[start]user count is ", count} {count=count+1;print $0;} END{print "[end]user count is ", count}' /etc/passwd
# NR是行数
awk -F ':' 'BEGIN {count=0;} {name[count] = $1;count++;}; END{for (i = 0; i < NR; i++) print i, name[i]}' /etc/passwd

echo "****************************"

# 正则表达式
# 将包含字符 smit，后跟一个或多个 h 字符，并以字符 ern 结束的字符串的任何记录打印至标准输出
echo "simthern"| awk '/simth+ern/'
# 将包含字符 smit，后跟零个或一个 h 字符的实例的所有记录打印至标准输出
echo "smithern"| awk '/simth?/'
# 将包含字符串 allen 或 alan 的所有记录打印至标准输出。此示例中的输出是
echo "xxalanxx"| awk '/allen | alan /'
# 将具有字符串 ae 或 alle 或 anne 或 allnne 的所有记录打印至标准输出
echo "alle" | awk '/a(ll)?(nn)?e/'

echo "****************************"
