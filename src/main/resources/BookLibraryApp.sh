#!/bin/bash

app="BookLibrary-1.0.0.jar"
maxInstance=1

	pidfile="BookLibrary.pid"
	logfile="config/logback.xml"
	configfile="config/application.properties"
	trxname="BookLibrary"
	trxlog="out-BookLibrary.log"

case $1 in
start)
	#check number of currently running instances
	if [ -f $pidfile ];
	then
		pids=$(<$pidfile)
		pidArr=($pids)
		
		if [ "$maxInstance" -le "${#pidArr[@]}" ];
		then
			echo "Too many instance running, please stop one of them"
			exit 2
		fi
	else
		#backup the previous log
		if [ -f $trxlog ];			
		then
			curdate=`date +%Y%m%d_%H%M%S`
			if [ ! -d "log" ];
			then
				mkdir "log"
			fi
			
		fi
	fi

	#start the transmiter
	echo "Starting $trxname"

	java -jar BookLibrary-1.0.0.jar --spring.config.location=file:$configfile -Dlogback.configurationFile=$logfile > $trxlog 2>&1 &

	pid=$!
	echo "$trxname started ($pid)"
	#rm $trxlog
	
	#save the pid into file	
	if [ -f $pidfile ];
	then
		echo -n " $pid" >> $pidfile
	else
		#backup the previous log
		if [ -f $trxlog ];			
		then
			curdate=`date +%Y%m%d_%H%M%S`
			
		fi
		echo -n $pid > $pidfile
	fi
	;;
stop)
	if [ -f $pidfile ];
	then
		#stop $trxname
		pids=$(<$pidfile)
		pidArr=($pids)
		
		lastPid=${pidArr[@]: -1:1}

		echo "Please wait for 2 Seconds to kill application"
		sleep 2
                kill -9 $lastPid
		echo "$trxname with pid=$lastPid has been stopped"

		#rewrite pidfile
		if [ 1 = ${#pidArr[@]} ];
		then
			rm -f $pidfile
		else
			afterPid=("${pidArr[@]/$lastPid}")
			echo -n "${afterPid[@]}" > $pidfile
		fi
	else
		echo "Previous instance not found, please start it first"
	fi
	;;
stop-all)
	#stop all $trxname
	pids=$(<$pidfile)

	echo "Stoping BookLibrary $trxname with pid: $pids"	        
	kill -9 $pids	
	echo "good bye my friend..."
	rm -f $pidfile	
	;;
*)
	echo "Command not recognize, please check parameters"
	exit 1
	;;
esac

exit 0
