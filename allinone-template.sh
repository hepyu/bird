profile=$1
javaAgent=$2

if [ ! -n "$profile" ] ;then
    echo "you have not input a profile in the postion $1."
    exit
fi

mkdir -p /data/bird/logs/com-bird-user-service/
mkdir -p /data/bird/logs/com-bird-lbs-service/
mkdir -p /data/bird/logs/com-bird-passport-service/
mkdir -p /data/bird/logs/com-bird-lbs-web/
mkdir -p /data/bird/logs/com-bird-passport-web/

chmod -R 777 /data/bird/logs

mvn -U -e clean package install -Dmaven.test.skip=true 

if [ ! -n "$profile" ] ;then
	echo "no support."
    #start service
	#nohup java -jar -Dspring.profiles.active=$profile com-bird-user/com-bird-user-service/target/com-bird-user-service-1.0-SNAPSHOT.jar >/dev/null 2>&1 &
	#nohup java -jar -Dspring.profiles.active=$profile com-bird-lbs/com-bird-lbs-service/target/com-bird-lbs-service-1.0-SNAPSHOT.jar >/dev/null 2>&1 &
	#sleep 20
	#nohup java -jar -Dspring.profiles.active=$profile com-bird-passport/com-bird-passport-service/target/com-bird-passport-service-1.0-SNAPSHOT.jar >/dev/null 2>&1 &
	#sleep 20

	#setart web 
	#nohup java -jar -Dspring.profiles.active=$profile com-bird-passport/com-bird-passport-web/target/com-bird-passport-web-1.0-SNAPSHOT.jar >/dev/null 2>&1 &
	#nohup java -jar -Dspring.profiles.active=$profile com-bird-lbs/com-bird-lbs-web/target/com-bird-lbs-web-1.0-SNAPSHOT.jar >/dev/null 2>&1 &
else
	#nohup java -javaagent:$javaAgent -jar -Dspring.profiles.active=$profile com-bird-user/com-bird-user-service/target/com-bird-user-service-1.0-SNAPSHOT.jar >/dev/null 2>&1 &
	nohup java -javaagent:$javaAgent -jar -Dspring.profiles.active=$profile com-bird-lbs/com-bird-lbs-service/target/com-bird-lbs-service-1.0-SNAPSHOT.jar >/dev/null 2>&1 &
	sleep 20
	#nohup java -javaagent:$javaAgent -jar -Dspring.profiles.active=$profile com-bird-passport/com-bird-passport-service/target/com-bird-passport-service-1.0-SNAPSHOT.jar >/dev/null 2>&1 &
	#sleep 20
	
	#setart web 
	#nohup java -javaagent:$javaAgent -jar -Dspring.profiles.active=$profile com-bird-passport/com-bird-passport-web/target/com-bird-passport-web-1.0-SNAPSHOT.jar >/dev/null 2>&1 &
	nohup java -javaagent:$javaAgent -jar -Dspring.profiles.active=$profile com-bird-lbs/com-bird-lbs-web/target/com-bird-lbs-web-1.0-SNAPSHOT.jar >/dev/null 2>&1 &
fi
