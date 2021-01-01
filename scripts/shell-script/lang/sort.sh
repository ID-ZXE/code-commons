#!/usr/bin/env bash

source ../../tools/statistics/beauty

wrap='\n'
text='str'${wrap}'str'${wrap}' str'${wrap}'abc'${wrap}'str'

# sort
echo -e "${text}" | sort
separate
# -b 忽略每行开始的空格字符
echo -e "${text}" | sort -b
separate
# -d 排序时,除了英文字母,数字,空格外.忽略其他
# -n 依照数值的大小排序
# -r 相反的顺序排序
echo -e "${text}" | sort -br
separate
# -f 排序时, 将小写字母视为大写字母
# -k 以哪个区间 (field) 来进行排序
# -o<输出文件>: 将排序后的结果存入指定的文件
