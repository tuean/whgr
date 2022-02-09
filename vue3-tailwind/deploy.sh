#!/bin/bash

echo 'work dir is:' `pwd`


if [ -d dist ];
then
    echo 'exist folder dist'
    rm -rf dist
else
    echo 'no dist'
fi

echo 'npm run build'
npm run build

sshpass ssh root@1.117.89.194 'rm -rf /opt/desktop/dist'
scp -r dist root@1.117.89.194:/opt/desktop

echo 'done'

