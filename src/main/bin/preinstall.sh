#!/bin/bash

if id -g "liquid" >/dev/null 2>&1; then
        echo "liquid group exists, skipping create"
else
        echo "liquid group does not exist, creating one"
        groupadd liquid
fi

if id "liquid" >/dev/null 2>&1; then
        echo "liquid user exists, skipping create"
else
        echo "liquid user does not exist, creating one"
        useradd liquid -g liquid
        usermod -a -G users liquid
fi