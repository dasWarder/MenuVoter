#!/bin/bash

#stop docker-compose container in the case if its still on running
docker-compose down

#clean and install build
mvn clean install

#start docker-compose on daemon
docker-compose up -d

#change package for moving to mongo liquibase.properties
cd menu-common/web/src/main/resources/db/changelog/mongo

#updating MongoDB database
liquibase update

#move to the web package
cd C:/Users/user/IdeaProjects/MenuVoter/menu-common/web

#updating PostgreSQL database with the help of maven liquibase plugin and postgre profile (without mongo.liquibase.ext dep)
mvn liquibase:update -P postgre



