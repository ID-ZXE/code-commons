# 两种定义方式
func1() {
    echo "func1"
}
function func2() {
    echo "func2"
}

func1
func2

# return返回值
# 1-255的整数
# 0成功, 1失败
func_return() {
    # echo "Enter a value: "
    # read value
    # echo "Double the value"
    # 返回状态码
    # return $[ ${value} * 2 ]
    return 100
}
func_return
echo $?

# echo返回值
func_echo_return() {
    local users=`cat /etc/passwd| cut -d: -f1`
    echo ${users}
}
result=`func_echo_return`
for user in ${result}
do
    echo ${user}
done

# 传递参数
# $# 传入的参数数量
func_param() {
    # eq 等于
    # gt 大于
    if [[ $# -eq 0 ]] || [[ $# -gt 2 ]]
    then
        echo -1
    elif [[ $# -eq 1 ]]
    then
        echo $[ $1 + $1 ]
    else
        echo $[ $1 + $2 ]
    fi
}
result=$(func_param 1 3)
echo "result is ${result}"