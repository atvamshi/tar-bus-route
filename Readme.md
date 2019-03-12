##Run Tests
mvn clean test

##package project
mvn clean package

##run project
java -jar target/app.jar


##access swagger
http://<host_name>/swagger-ui.html
e.g accessing swagger on local machine running on 8080
http://localhost:8080/swagger-ui.html

## access bus time calc api
http://<HOST_NAME>:<PORT>/app/bus/<ROUTE_NAME>/<DIRECTION>/<STOP_NAME>

e.g http://localhost:8080/app/bus/METRO Blue Line/SOUTH/Mall of America Station