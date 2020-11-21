#!/usr/bin/env bash
# 处理时只能处理相近的两条重复行
# 一般使用时配合sort使用

# 输出删除重复行的内容
uniq ../../file/text8.txt
# 检查文件并删除文件中重复出现的行，并在行首显示该行重复出现的次数
uniq -c ../../file/text8.txt
# 找出重复行
uniq -d ../../file/text8.txt

# 与sort配合使用
sort ../../file/text9.txt |uniq