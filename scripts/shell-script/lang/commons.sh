# 获取命令结果
# 单条命令
# result=`command`
# result=$(command)
# 可以执行多条命令
# result=$(pwd;ls)

cut_etc_passwd() {
  index=1
  for user in $(cat /etc/passwd | cut -d ":" -f 1); do
    echo "${index}" + "${user}"
    index=$((index + 1))
  done
}

calculation() {
  num1=10
  num2=20
  res1=$((num1 + num2))
  res2=$(($num1 + $num2))
  echo "${res1}"
  echo "${res2}"
}

# 判定nginx进程是否存在
protect_nginx() {
  local process_num=$(ps -ef | grep 'nginx' | grep -v 'grep' | wc -l)
  echo "${process_num} nginx is running"
}

########################################
# 通用技巧
# 1. 获取当前脚本的目录
base_dir=$(cd $(dirname $0); pwd)
echo ${base_dir}
# 2. 判断需要进入的目录是否存在, 不存在则退出
cd /home || exit 1


calculation