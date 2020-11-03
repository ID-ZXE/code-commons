#!/usr/bin/env bash
# 变量替换
# ${变量#匹配规则}  从开头开始匹配, 最短删除
# ${变量##匹配规则} 从开头开始匹配, 最长删除
# ${变量*匹配规则}  从结尾开始匹配, 最短删除
# ${变量**匹配规则} 从结尾开始匹配, 最长删除
# ${变量/旧字符串/新字符串}   替换旧的为新的, 只替换第一个
# ${变量//旧字符串/新字符串}  替换新的为旧的, 替换所有
var='i love you, do you love me'
# 删除ov开头的数据, 从头开始最短的那个
var1=${var#*ov}
echo "${var1}"
# 删除ov开头的数据, 从头开始最长的那个
var2=${var##*ov}
echo "${var2}"

echo "${PATH}"
var3=${PATH/bin/BIN}
echo "${var3}"

# 字符串处理
# 字符串长度
str="hello world"
echo length "${#str}"
# 获取子串
# 从0位置开始
echo "${str:0}"
# 需要有空格
echo "${str: -1}"
# 从0位置开始, 获取多少位的
echo "${str:0:5}"

# expr
# mac os zsh 无法执行expr
# len=$(expr length "$str")
# echo length "${len}"
# 获取字符索引位置
# index=$(expr index "$str" "world")
# echo "${index}"
# 获取子串
# expr substr ${str} ${position} ${length}

echo "****************"
# 需求
# string="bigdata process framework is Hadoop, Hadoop is an open source project"
# 1. 打印string长度
# 2. 删除所有Hadoop
# 3. 替换第一个Hadoop为MapReduce
# 4. 替换全部Hadoop为MapReduce
string="bigdata process framework is Hadoop, Hadoop is an open source project"
echo 1. "${#string}"
echo 2. "${string//Hadoop/}"
echo 3. "${string/Hadoop/}"
echo 4. "${string//Hadoop/MapReduce}"

echo "****************"
func main
{
    while true; do
        echo "[string=${string}]"
        echo
        read -r -p "Pls input your choice (1|2|3|4|q|Q): " choice
        case $choice in
        1)
            echo 1. "${#string}"
            ;;
        2)
            echo 2. "${string//Hadoop/}"
            ;;
        3)
            echo 3. "${string/Hadoop/}"
            ;;
        4)
            echo 4. "${string//Hadoop/MapReduce}"
            ;;
        q | Q)
            exit
            ;;
        *)
            echo "Error Input"
            exit
            ;;
        esac
    done
}
# zsh不兼容read -p
# main
