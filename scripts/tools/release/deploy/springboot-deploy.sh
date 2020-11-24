APP_NAME='spring-boot-web-app'

CATALINA_HOME=${HOME}/app/${APP_NAME}
LOG_DIR=${CATALINA_HOME}/logs
DATE=$(date '+%Y%m%d')
OPERATION=''

main() {
	parse_args "$@"
	case ${OPERATION} in
	start)
		echo 'start app'
		;;
	stop)
		echo 'stop app'
		;;
	restart)
		echo 'restart app'
		;;
	*)
		echo 'error operation\nquit'
		exit 1
		;;
	esac
}

parse_args() {
	while getopts ":a:o:" opt; do
		case $opt in
		a)
			APP_NAME=${OPTARG}
			;;
		o)
			OPERATION=${OPTARG}
			;;
		?)
			echo "参数错误"
			exit 1
			;;
		esac
	done
}

start() {
	PID=$(ps -ef | grep 'java -jar ${APP_NAME}' | awk '{print $2}')
	# 获取到的PID不为空
	if [[ -n ${PID} ]]; then
		# -D参数需要放在jar之前
		# springboot的application.properties的参数还可以通过--param=value的方式动态注入
		nohup java -jar ${JAVA_OPTS} -Dserver.tomcat.basedir=${CATALINA_HOME} -Dlogs.dir=${LOG_DIR} ${APP_NAME}.jar >>${LOG_DIR}/catalina.out 2>&1 &
	else
		echo 'application ${APP_NAME} has started'
		return 1
	fi
}

stop() {
	PID=$(ps -ef | grep 'java -jar ${APP_NAME}' | awk '{print $2}')
	# 获取到的PID不为空
	if [[ -n ${PID} ]]; then
		kill PID
		echo 'kill process ${PID}'
		sleep 10s
		PID=$(ps -ef | grep 'java -jar ${APP_NAME}' | awk '{print $2}')
		if [[ -n ${PID} ]]; then
			kill -9 PID
			# 强制退出
			echo 'process ${PID} force quit'
		else
			echo 'process ${PID} normal quit'
		fi
	fi
}

restart() {
	stop
	start
}

check_service() {
	echo 'check-service'
}

main "$@"
