#!/bin/bash

source beauty.sh
wrap='\n'

# 词频统计
text='str abc'${wrap}'abc   str'${wrap}'str'${wrap}'abc'${wrap}'str'
echo -e "${text}" | tr -s ' ' '\n' | sort | uniq -c | sort -r | awk '{print $2, $1}'
separate

# ip访问量前2
time=$(date +"%Y-%m-%d %H:%M:%S")
ips=${time}' 127.0.0.1'${wrap}${time}' 127.0.0.2'${wrap}${time}' 127.0.0.1'${wrap}${time}' 127.0.0.2'${wrap}${time}' 127.0.0.3'${wrap}${time}' 127.0.0.2'
echo -e "${ips}"
separate
echo -e "${ips}" | awk '{print $3}' | sort | uniq -c | sort -r | awk '{print $2}' | head -2
separate

# 只打印第2行
text='line1'${wrap}'line2'${wrap}'line3'${wrap}'line4'${wrap}'line5'
echo -e "${text}"
separate
echo -e "${text}" | awk '{if(NR==2){print $0}}'
separate

# 转置
text='name age'${wrap}'alice 21'${wrap}'ryan 30'
echo -e | awk '
{
    for (i = 1; i <= NF; i++) {
        if (NR == 1) {
            res[i] = $i
        } else {
            res[i] = res[i]" "$i
        }
    }
}
END{
    for (j = 1; j <= NF; j++) {
        print res[j]
    }
}'