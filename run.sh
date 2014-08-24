#!/bin/bash

mvn clean compile
sudo chmod +x target/classes/driver/Linux/amd64/chromedriver
sudo chmod +x target/classes/driver/Linux/x86/chromedriver
mvn verify