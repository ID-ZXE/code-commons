#!/bin/bash
# Linux tr 命令用于转换或删除文件中的字符

text='str1 str2  str3   str4'

# tr [option] [SET1] [SET2]

# tr -c 反选设定字符, 也就是符合SET1的部份不做处理, 不符合的剩余部份才进行转换
echo -e "${text}" | tr -c 'str' '-'
echo ''
# tr -d 删除指令字符
echo -e "${text}" | tr -d ' '
# tr -s 缩减连续重复的字符成指定的单个字符
echo -e "${text}" | tr -s ' ' '\n'
