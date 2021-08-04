#!/bin/bash

mvn spring-boot:run -pl menu-common/web -P=postgre &

cd frontend

npm start