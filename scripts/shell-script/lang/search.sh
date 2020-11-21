# find 在磁盘中搜索
# find 路径 选项 操作
# 选项
# -name         根据文件名称查找
# -perm         根据文件权限查找
# -prune        排除某些目录查找
# -user         根据文件属主查找
# -group        根据文件属组查找
# -mtime -n|+n  根据文件更改查找
# -type         文件类型
# -size  -n|+n  文件大小
# -mindepth n   从n级子目录开始搜索
# -maxdepth n   最多搜索n级子目录

# 搜索etc目录
# find /etc/ *.conf
# 查找当前目录下名为aa的文件
# find . -name array.sh

# type
# f 文件
# d 目录
# c 字符设备文件
# d 块设备
# l 链接文件
# p 管道文件
find . -type f

# 文件大小小于1000字节
find -size -1000c
# 文件大小大于10MB
find -size +10M
# 文件大小等于
find -size 100k

# 查找修改时间在五天以内的
find -mtime -5
# 查找修改时间在五天以外的
find -mtime +5
# 查找修改时间正好五天的
find -mtime 5

# 查找修改时间在5min以内的
find -mmin -5
# 查找修改时间在5min以外的
find -mmin +5
# 查找修改时间正好5min的
find -mmin 5

# 查找权限为777的文件
find -perm 777

# prune
# 查找当前目录, 排除./logs目录
# 命令格式: -path xxx目录 -prune -o
find . -path ./logs -prune -o -type f
find . -path ./logs -prune -o -path ./test -prune -o -type f

# whereis
# 查找二进制程序文件
# -b  只返回二进制文件
# -m  只返回帮助文档
# -s  只返回源代码文件
whereis find

# which
# 查找程序的可执行文件
which find