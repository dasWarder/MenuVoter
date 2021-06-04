#!/bin/bash

#down the docker container
docker-compose down

#clean jar due to not using the old build again
mvn clean