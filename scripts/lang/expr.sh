# 数学计算
# 1. expr $num1 operation $num2
# 2. $(($num1 operation $num2))

num1=10
num2=20
num3=30

# 操作符需要转义
# num1不为空且非0, 返回num1, 否则num2
expr ${num1} \| ${num2}
# num1不为空且非0, 返回num1, 否则0
expr ${num1} \& ${num2}
# 比较
# num1小于num2, 返回1, 否则返回0
expr ${num1} \< ${num2}
# 类似
expr ${num1} \> ${num2}
# 计算
expr ${num1} \* ${num2}

num=1
expr ${num} + 1 &> /dev/null
# 判断num是不是数字
if [[ $? -eq 0 ]]; then
  if [[ $(expr $num \> 0) -eq 1]]; then
    echo "nice"
  else
    echo "error"
  fi
fi


#bc 计算器
echo "1+2" | bc
echo "1.1+2.2" | bc
# scale=4 精确到小数点后四位
echo "scale=4; 5/2" | bc
echo "a=1;b=2; a+b" | bc