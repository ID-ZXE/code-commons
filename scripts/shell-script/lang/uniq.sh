#!/usr/bin/env bash

source ../../tools/statistics/beauty

# 处理时只能处理相近的两条重复行
# 一般使用时配合sort使用

wrap='\n'
text='str'${wrap}'str'${wrap}'str'${wrap}'abc'${wrap}'str'

# 输出删除重复行的内容
echo -e "${text}" | uniq
separate
# 检查文件并删除文件中重复出现的行, 并在行首显示该行重复出现的次数
echo -e "${text}" | uniq -c
separate
# 找出重复行
echo -e "${text}" | uniq -d
separate
# 与sort, awk配合使用
echo -e "${text}" | sort | uniq -c | awk '{print $2, $1}'
separate