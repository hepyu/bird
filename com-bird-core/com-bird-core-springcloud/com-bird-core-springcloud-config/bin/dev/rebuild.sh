#!/bin/sh
cd ../../
git pull
sudo rm -f release/lib/*
mvn clean install dependency:copy-dependencies -DoutputDirectory=release/lib -DincludeScope=runtime -Dsilent=true
mv target/apollo-cloud-system-config-1.0.4-SNAPSHOT.jar release/lib