cd ../../apps/com-bird-core-springcloud-config
broker=$1
APP_NAME=com-bird-core-springcloud-config
PID_FILE=$APP_NAME-$broker.pid
# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

# Get standard environment variables
PRGDIR=`dirname "$PRG"`

# Only set CATALINA_HOME if not already set
CONFIG_HOME=`cd "$PRGDIR" >/dev/null; pwd`
echo "CONFIG_HOME: $CONFIG_HOME"

CONFIG_CONF=$(dirname $(dirname $CONFIG_HOME))/conf/$APP_NAME
CONFIG_CP=.:$CONFIG_CONF'/'
echo "CONFIG_CP: $CONFIG_CP"

CONFIG_LOG=$(dirname $(dirname $CONFIG_HOME))/logs/$APP_NAME
mkdir -p $CONFIG_LOG
echo "CONFIG_LOG: "$CONFIG_LOG

export CONFIG_HOME
export CONFIG_CP
for file in `find $CONFIG_HOME/lib -name '*jar' | sort` ;
do
        CONFIG_CP=$CONFIG_CP:$file;
done;

export CONFIG_CP

#echo "CONFIG_CP:$CONFIG_CP"

echo "java -Xms128m -Xmx128m -XX:NewRatio=3 -XX:SurvivorRatio=4 -XX:MaxTenuringThreshold=4 -XX:TargetSurvivorRatio=90 -XX:+PrintTenuringDistribution -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Dspring.config.location=$CONFIG_CONF/application-dev-$broker.yml -cp $CONFIG_CP com.apollo.cloud.system.config.ApolloConfigApplication > com-bird-core-springcloud-config-$broker.log 2>&1 &"

shift
mv com-bird-core-springcloud-config-$broker.log com-bird-core-springcloud-config-$broker.log.`date +%y-%m-%d-%H-%M` 2> /dev/null
java -Xms128m -Xmx128m -XX:NewRatio=3 -XX:SurvivorRatio=4 -XX:MaxTenuringThreshold=4 -XX:TargetSurvivorRatio=90 -XX:+PrintTenuringDistribution -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -cp $CONFIG_CP com.bird.core.springcloud.config.BirdConfigApplication > $CONFIG_LOG/com-bird-core-springcloud-config-$broker.log 2>&1 &

echo $! > $CONFIG_HOME/$PID_FILE
exit 0;
