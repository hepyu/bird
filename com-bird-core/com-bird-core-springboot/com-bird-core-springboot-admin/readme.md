1.关于打包&部署&启动的目录结构：
resource下根据环境区分，需要把当前环境对应的配置文件copy到resource下。打包部署启动的shell脚本要注意。

配置参考
https://www.jianshu.com/p/e2bebfb0d075

(1).standalone模式：
eclipse启动，增加VM启动参数：-Dspring.config.location=classpath:/dev/application-dev-standalone.yml

(2).cluster模式：
eclipse启动，增加VM启动参数：
-Dspring.config.location=classpath:/dev/application-dev.yml --spring.profiles.active=usa
-Dspring.config.location=classpath:/dev/application-dev.yml --spring.profiles.active=ldn
-Dspring.config.location=classpath:/dev/application-dev.yml --spring.profiles.active=sgr