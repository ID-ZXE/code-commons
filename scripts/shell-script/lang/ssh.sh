#!/usr/bin/env bash
# 单条命令
ssh nick@xxx.xxx.xxx.xxx "ls"
# 多条命令
ssh nick@xxx.xxx.xxx.xxx "pwd; cat hello.txt"
# 交互式命令
ssh -t nick@xxx.xxx.xxx.xxx "top"
# 远程执行本地脚本
ssh nick@xxx.xxx.xxx.xxx <./test.sh
# 远程执行本地脚本, 且传递参数
ssh nick@xxx.xxx.xxx.xxx 'bash -s' hello <test.sh
# 使用本地变量
name=test
ssh nick@xxx.xxx.xxx.xxx 'bash -c' "'echo $name'"
