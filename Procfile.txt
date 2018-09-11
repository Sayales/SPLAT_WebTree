web:    java $JAVA_OPTS -DWEBTREE_ROOT="." -jar target/dependency/webapp-runner.jar --port $PORT target/*.war
