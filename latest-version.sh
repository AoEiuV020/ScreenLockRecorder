#!/bin/sh
cur="$(dirname $0)"
cd $cur
changeLogFile=./ChangeLog.txt
cat $changeLogFile |head -2 |tail -1 |sed  's/\(.*\):/\1/'
