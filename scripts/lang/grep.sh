#!/usr/bin/env bash
# 该命令用来查找与给定的字符串相匹配文件的行
# 1. 命令格式 grep [option] [pattern] [file1,file2...]
# 2. 命令格式 command| grep [option] [pattern]

# fgrep命令查询速度比grep快,但是它不够灵活,不能用正则表达式 该命令等价于grep -F
# fgrep -n "aa bb cc" ../../file/text4.txt

# grep -i 搜索时忽略大小写
# grep -n 显示行号
# grep -r 递归搜索当前文件夹中的文件内容
# grep -E 正则表达式搜索(扩展正则表达式)
# egrep直接支持扩展的正则表达式
# grep -F 按照字符字面意思, 不按照正则表达式搜索

# grep -v 排除
echo -e "hello \n world" | grep -v "world"
# 显示file文件里匹配foo字串那行以及上下5行
grep -C 5 "foo" file
# 显示foo及前5行
grep -B 5 "foo" file
# 显示foo及后5行
grep -A 5 "foo" file


# grep -c 输出匹配的行数
# grep -w 匹配单词
# grep -x 匹配整行, 就是整行都完全匹配才找出来
# grep -l 输出匹配的文件名, 不输出内容