#!/bin/bash
pushd no.resheim.elibrarium-parent
mvn clean verify -Posx
popd
