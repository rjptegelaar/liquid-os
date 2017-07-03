#!/bin/bash
if id "liquid" >/dev/null 2>&1; then
        echo "liquid user exists, skipping create"
else
        echo "liquid user does not exist, creating one"
        useradd liquid -G liquid
fi