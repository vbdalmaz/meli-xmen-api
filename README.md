# Backend Test

I'm using Spring Boot with Java 8, Redis for cache and MongoDB as Database

# Docker 
To run local just install java 8 and docker

docker run --name some-mongo -d -p 27017:27017 mongo

docker run --name some-redis -d -p 6379:6379 redis

Go to project folder and execute:
mvn spring-boot:run

# Vagrant Script 
If you prefer you can use my vagrant script to deploy: 
https://github.com/vbdalmaz/meli-xmen-api-devops/

# Amazon Swagger Link
http://ec2-18-228-220-94.sa-east-1.compute.amazonaws.com:8080/melixmen/swagger-ui.html

# React Frontend Link
http://ec2-18-231-121-204.sa-east-1.compute.amazonaws.com:3000

# React Frontend GIT
https://github.com/vbdalmaz/meli-xmen-frontend




