kibana

```shell
docker run -d \
  --name kibana \
  --link elasticsearch \
  --restart=always \
  -e ELASTICSEARCH_URL=http://elasticsearch:9200 \
#  -v /Users/tuean/docker/kibana.yml \
  -p 5601:5601 \
  kibana:7.9.2
```


```markdown
docker run 创建并启动容器
-d 后台运行 
--name kibana 指定容器唯一的名称，方便管理
--link elasticsearch 使kibana与elasticsearch同处于一个网络方便主机名通讯
-p 5601:5601 映射容器端口到宿主机上
-e ELASTICSEARCH_URL=http://elasticsearch:9200 环境变量配置ES地址
kibana:7.9.2 镜像名称及版本
```

