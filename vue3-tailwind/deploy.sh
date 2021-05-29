#!/bin/bash

server='1.117.89.194'
user='root'
pwd='Tuean_330@3'

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


sshpass -p $pwd ssh $user@$server 'rm -rf /opt/desktop/dist'
sshpass -p $pwd scp -r dist $user@$server:/opt/desktop/ 

echo 'done'

