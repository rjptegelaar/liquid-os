#!/bin/bash

copy1=cp /opt/liquid-os/LiquidService /etc/init.d/

echo "Copied service file to init.d $copy1"

chmod1=chmod +x /etc/init.d/LiquidService

echo "Added execute rights to service file $chmod1"

chmod2=chmod +x /opt/liquid-os/setup.sh

echo "Added execute rights to setup file $chmod2"

chown1=chown -R liquid:liquid /opt/liquid-os/

echo "Changed ownership of home dir $chown1"