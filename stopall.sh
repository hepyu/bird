#!/usr/bin/env bash

ps -ef | grep com-bird | grep java | awk '{print $2}' | xargs -p kill -9