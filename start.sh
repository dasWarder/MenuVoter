#!/bin/bash

docker-compose down

mvn clean install

docker-compose up -d

cd menu-common/web/src/main/resources/db/changelog/mongo

liquibase update

cd C:/Users/user/IdeaProjects/MenuVoter/menu-common/web

mvn liquibase:update -P postgre



