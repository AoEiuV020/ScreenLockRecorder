#!/bin/sh
# ./bump-version 下个版本，
# 改版本号和版本名，然后提交，
set -e
old=$PWD
cd $(dirname $0)
project=$(pwd)
buildGradleFile="$project/build.gradle"
versionFile="$project/version.properties"

versionCode=$(sed -n 's/\s*version_code\s*=\s*\(\S*\)/\1/p' $versionFile)
versionCode=$(expr $versionCode + 1)

sed -i "s/version_code\\s*=\\s*[0-9]*/version_code=$versionCode/" $versionFile

versionName=$1
sed -i "s/version_name\\s*=\\s*.*/version_name=$versionName/" $versionFile

git add $versionFile
git commit -m "Bumped version number to $versionName"

cd $old
