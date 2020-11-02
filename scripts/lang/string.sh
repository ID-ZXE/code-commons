# 变量替换
# ${变量#匹配规则}  从开头开始匹配, 最短删除
# ${变量##匹配规则} 从开头开始匹配, 最长删除
# ${变量*匹配规则}  从结尾开始匹配, 最短删除
# ${变量**匹配规则} 从结尾开始匹配, 最长删除
# ${变量/旧字符串/新字符串}   替换旧的为新的, 只替换第一个
# ${变量//旧字符串/新字符串}  替换新的为旧的, 替换所有
var='i love you, do you love me'
var1=${var#*ov}
echo "${var1}"
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
