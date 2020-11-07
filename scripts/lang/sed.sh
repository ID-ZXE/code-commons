#!/usr/bin/env bash
# 命令格式: command| sed [option] 'pattern'
# 命令格式: sed [option] 'pattern' file

text="
i love python
i love python
I LOVE PYTHON
Hadoop is bigdata framework
"

# s命令，使用第三个替换第二个
# 只替换第一个
echo "${text}" | sed "s/python/java/"
# 替换所有
echo "test this is a test" | sed "s/test/big test/g"

# 从文件读取多行命令
# sed -f ./command.sed ./text.txt

# s/pattern/replacement/flag
# 其中flag有四种
# 数字: 代表替换第几处的文本
# g: 代表新文本将会替换所有匹配的文本
# p: 表明原先行的内容需要打印出来
# w file: 将替换的结果写到file文件中去

#[option]
# -e 多个编辑命令使用-e命令加上;号分割
sed -e "s/this/there/;s/test/big test/" ./text.txt
# -n 禁止sed编辑器输出
# 和p标记结合代表输出修改过的行
echo "test this is a test" | sed -n "s/test/big test/p"
# -r 支持扩展正则
# -i 直接修改文件内容
# -f 编辑动作保存在文件中, 执行文件执行
