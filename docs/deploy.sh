#!/bin/bash


echo 'work dir is:' `pwd`


if [ -d ./docs/.vuepress/dist ];
then
    echo 'exist folder dist'
    rm -rf dist
else
    echo 'no dist'
fi

echo 'npm run docs:build'
npm run docs:build

sshpass ssh root@150.158.97.155 'rm -rf /opt/whgr/dist'
scp -r ./docs/.vuepress/dist root@150.158.97.155:/opt/whgr

echo 'done'

