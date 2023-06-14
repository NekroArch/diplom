#!/bin/bash

if [ $(systemctl is-failed docker.service) == inactive ]; then
    sudo systemctl start docker
fi

sudo docker-compose -f ../docker-compose.yaml up

