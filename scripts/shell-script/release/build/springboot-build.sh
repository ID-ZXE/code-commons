GIT_ADDRESS='git@github.com:plum-wine/code-commons.git'
PROFILE=''
APP_NAME=''
CODE_DIR=${HOME}/ci/code/${APP_NAME}
BUILD_RESULT_DIR=${HOME}/ci/build_result/${APP_NAME}

main() {
	parse_args "$@"
	# get_code
	# build
}

parse_args() {
	while getopts ":a:g:p:b:" opt; do
		case $opt in
		a)
			APP_NAME=${OPTARG}
			echo "build ${APP_NAME}"
			;;
		g)
			GIT_ADDRESS=${OPTARG}
			echo "git repo : ${GIT_ADDRESS}"
			;;
		p)
			PROFILE=${OPTARG}
			echo "profile : ${PROFILE}"
			;;
		?)
			echo "参数错误"
			exit 1
			;;
		esac
	done
}

get_code() {
	rm -rf ${CODE_DIR}
	git clone ${GIT_ADDRESS} ${CODE_DIR}
}

build() {
	cd ${CODE_DIR}
	mvn clean package -P${PROFILE} -Dapp.name=${APP_NAME}
}

# 压缩编译后的文件到指定目录
compress() {
	echo 'compress'
}

main "$@"
