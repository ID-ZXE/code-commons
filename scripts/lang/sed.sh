#!/usr/bin/env bash
# 命令格式: command| sed [option] 'pattern'
# 命令格式: sed [option] 'pattern' file
# sed [option] 'pattern'
# 注意事项
# pattern如果使用到了变量, 则需要使用双引号, 而不是单引号, 否则sed会将变量引用的语法认为是普通字符串
# 如果外面使用了单引号, 则变量引用处也需要加上单引号

#[option]
# -n选项, 只打印模式匹配的行, 不加这个选项会把所有的行输出
sed -n '/python/p' ../test/sed.txt
# -e选项, 支持多个匹配模式
sed -n -e '/python/p' -e '/PYTHON/p' ../test/sed.txt
# -f选项, pattern保存在文件中, 从文件中读取命令执行
# sed -n -f ../test/command.sed ../test/sed.txt
# -i选项, 直接修改文件内容
# sed -i -e "s/love/like/g" ../test/sed.txt
# -r选项, 支持扩展正则
# sed -n -r '/python|PYTHON/p' ../test/sed.txt

# pattern
# s命令, 编辑, 使用第三个替换第二个
# 将行内第一个xxx替换为yyy
echo "xxx xxx xxx xxx" | sed "s/xxx/yyy/"
# 将行内所有xxx替换为yyy
echo "xxx xxx xxx xxx" | sed "s/xxx/yyy/g"
# 将第二个xxx替换为yyy
echo "xxx xxx xxx xxx" | sed "s/xxx/yyy/2"
# 将第二个开始的, 匹配xxx的, 替换为yyy
echo "xxx xxx xxx xxx" | sed "s/xxx/yyy/2g"
# 将行内所有xxx替换为yyy, 忽略大小写
echo "xxx xxx xxx XXX" | sed "s/xxx/yyy/ig"
# 将所有xx开头的四字单词, 替换为xxyy
echo "xxyy xxaa xxbb xxcc" | sed "s/xx../xxyy/g"
# 将所有xx开头的, 以a结尾的四字单词, 替换为xxyy
echo "xxyy xxaa xxbb xxcc" | sed "s/xx.a/xxyy/g"
# 将所有xx开头的四字单词, 后面追加s
# &表示引用匹配到的字符串
echo "xxyy xxaa xxbb xxcc" | sed "s/xx../&s/g"
# 使用()包住xx, ()需要转移, 将符合xx..的字符串替换为xxs, \1表示引用被()包住的字符串
echo "xxyy xxaa xxbb xxcc" | sed "s/\(xx\)../\1s/g"

# a命令, 行后追加
# 在满足xxx的行后, 追加一行yyy
echo "xxx xxx xxx xxx" | sed "/xxx/a yyy"
# i命令, 行前追加
# 在满足xxx的行前, 追加一行yyy
echo "xxx xxx xxx xxx" | sed "/xxx/i yyy"
# r命令, 指定文件内容, 行后追加
echo "xxx xxx xxx xxx" | sed "/xxx/r ../test/sed.txt"
# w命令, 匹配行写入到外部文件(覆盖), 文件可以事先不存在
# echo "xxx xxx xxx xxx" | sed "/xxx/w ../test/sed.txt"

# d命令, 删除
# 删除第一行
sed -i '1d' ../test/sed.txt
# 删除匹配xxx的行
sed -i '/xxx/d' ../test/sed.txt

# p命令, print
# 打印第二行
sed -n '2p' ../test/sed.txt
# 打印第一到第二行
sed -n '1,2p' ../test/sed.txt
# 打印第一行, 以及之后的一行
sed -n '1,+1p' ../test/sed.txt
# 打印匹配正则的行
# 以i开头的行
sed -n '/^i/p' ../test/sed.txt

# =命令, 显示行号
# 显示匹配了xxx的行的行号
sed -n '/xxx/=' ../test/sed.txt