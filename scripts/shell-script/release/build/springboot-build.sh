# 开启debug
set -x
# 参数
GIT_ADDRESS=
GIT_BRANCH=
PROFILE=
APP_NAME=
MAVEN_MODULE=
CODE_DIR=
BUILD_RESULT_DIR=

main() {
	# 解析参数
	parse_args "$@"
	# 参数初始化
	init
	# 获取代码
	get_code
	# 编译
	build
}

parse_args() {
	while getopts ":a:b:g:p:m:" opt; do
		case $opt in
		a)
			APP_NAME=${OPTARG}
			;;
		b)
			GIT_BRANCH=${OPTARG}
			;;
		g)
			GIT_ADDRESS=${OPTARG}
			;;
		p)
			PROFILE=${OPTARG}
			;;
		m)
			MAVEN_MODULE=${OPTARG}
			;;
		?)
			echo "参数错误"
			exit 1
			;;
		esac
	done
}

init() {
	echo "############################"
	echo "param init"
	echo "app name : ${APP_NAME}"
	echo "git repo : ${GIT_ADDRESS}"
	echo "git branch : ${GIT_BRANCH}"
	echo "profile : ${PROFILE}"
	echo "maven module : ${MAVEN_MODULE}"
	echo "############################"

	CODE_DIR=${HOME}/ci/code/${APP_NAME}
	BUILD_RESULT_DIR=${HOME}/ci/build_result/${APP_NAME}
}

get_code() {
	rm -rf ${CODE_DIR}
	mkdir -p ${CODE_DIR}
	git clone ${GIT_ADDRESS} ${CODE_DIR}
}

build() {
	git checkout ${GIT_BRANCH}
	# maven命令必须进入到pom.xml的目录中进行执行
	cd ${CODE_DIR}
	mvn clean package -P${PROFILE} -pl ${MAVEN_MODULE} -am -Dapp.name=${APP_NAME}
	# 移动编译后的文件到指定目录
	mkdir -p ${BUILD_RESULT_DIR}
	mv ${CODE_DIR}/${MAVEN_MODULE}/target/${APP_NAME}.jar ${BUILD_RESULT_DIR}
}

# sh build/springboot-build.sh -a web-app -b master -p dev -g git@github.com:plum-wine/spring-learn.git -m spring-boot-app/spring-boot-web
main $@
