#!/usr/bin/env bash
# 返回这个脚本文件放置的目录 并可以根据这个目录来定位所要运行程序的相对位置 绝对位置除外
basedir=$(
  cd "$(dirname .)"
  pwd
)
echo "${basedir}"

dir=$(
  cd "$(dirname "${basedir}")"
  pwd
)
echo "${dir}"
