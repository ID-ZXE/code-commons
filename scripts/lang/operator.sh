#!/usr/bin/env bash

# shell运算符
# 算数运算符 + - *(使用的时候可能需要转义) /  % = (赋值) == (相当) !=

# 关系运算符 -eq(等于) -ne(不等于) -gt(大于) -lt(小于) -ge(大于等于) -le(小于等于)

# 布尔运算符 ! -o(或) -a(&)

# 逻辑运算符 && ||

# 字符串运算符 = !=  -z(为空) -n(不为空) str(存不存在)

# 文件测试运算符 -b -c -d -f -g -k -p -u -r -w -x -s -e

# -e 判断对象是否存在
# -d 判断对象是否存在，并且为目录
# -f 判断对象是否存在，并且为常规文件
# -L 判断对象是否存在，并且为符号链接
# -h 判断对象是否存在，并且为软链接
# -s 判断对象是否存在，并且长度不为0
# -r 判断对象是否存在，并且可读
# -w 判断对象是否存在，并且可写
# -x 判断对象是否存在，并且可执行
# -O 判断对象是否存在，并且属于当前用户
# -G 判断对象是否存在，并且属于当前用户组
# -nt 判断file1是否比file2新  [ "/data/file1" -nt "/data/file2" ]
# -ot 判断file1是否比file2旧  [ "/data/file1" -ot "/data/file2" ]