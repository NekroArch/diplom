#!/bin/bash
sudo docker run -d --name postgresql -p 5432:5432 -e POSTGRES_USER=pguser -e POSTGRES_PASSWORD=pgpassword -e POSTGRES_DB=pizzeria_db -d postgres
