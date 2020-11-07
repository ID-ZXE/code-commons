array=(1 2 3 4 5)
# 通过下标获取
echo "${array[1]}"
# 获取全部
echo "${array[*]}"
arr=()
echo "${arr[1]}"


# 声明类型为数组
declare -a var_arr=(1 2 3 4 5)
echo ${var_arr[0]}
echo ${var_arr[1]}
echo ${var_arr[@]}
# 切片访问
echo ${var_arr[@]:1:2}
echo ${#var_arr}
# 清除元素
unset var_arr[0]
echo "clear index ${var_arr[0]}"
# 清除数组
unset var_arr
echo "clear arr ${var_arr[@]}"