#!/usr/bin/env bash
echo $0

# s命令，使用第三个替换第二个
# 只替换第一个
echo "test this is a test" | sed "s/test/big test/"
# 替换所有
echo "test this is a test" | sed "s/test/big test/g"
echo "****************************"

# 多个编辑命令使用-e命令加上;号分割
sed -e "s/this/there/;s/test/big test/" ../../file/text7.txt
echo "****************************"

# 从文件读取多行命令
sed -f ./command.sed  ../../file/text7.txt
echo "****************************"

# s/pattern/replacement/flag
# 其中flag有四种
# 数字：代表替换第几处的文本
# g：代表新文本将会替换所有匹配的文本
# p：表明原先行的内容需要打印出来
# w file：将替换的结果写到file文件中去

# -n表示禁止sed编辑器输出，和p标记结合代表输出修改过的行
sed -n 's/test/trial/p' ../../file/text7.txt