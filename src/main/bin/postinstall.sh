#!/bin/bash

cp /opt/liquid-os/LiquidService /etc/init.d/

chmod +x /etc/init.d/LiquidService

chmod +x /opt/liquid-os/setup.sh

chown -R liquid:liquid /opt/liquid-os/