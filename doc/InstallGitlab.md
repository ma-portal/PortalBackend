# Install Gitlab

Main References: https://www.jianshu.com/p/080a962c35b6

## 拉取并运行gitlab

```
$ docker run -d -p 443:443 -p 80:80 -p 22:22 --name gitlab --restart always -v /home/gitlab/config:/etc/gitlab -v /home/gitlab/logs:/var/log/gitlab -v /home/gitlab/data:/var/opt/gitlab gitlab/gitlab-ce
# -d：后台运行
# -p：将容器内部端口向外映射
# --name：命名容器名称
# -v：将容器内数据文件夹或者日志、配置等文件夹挂载到宿主机指定目录
```

如果出现端口占用，替换宿主机端口：
```
$ docker run -d -p 8443:443 -p 8080:80 -p 22:22 --name gitlab --restart always -v /home/gitlab/config:/etc/gitlab -v /home/gitlab/logs:/var/log/gitlab -v /home/gitlab/data:/var/opt/gitlab gitlab/gitlab-ce
```