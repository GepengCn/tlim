### ubuntu 16.04 64位部署openfire
> 安装mysql <br>
> 开启远程连接mysql <br>
> mysql 中文乱码 <br>
> openfire数据库及表创建 <br>
> 安装jdk <br>
> 使用FileZilla远程ubuntu上传openfire服务<br>
> 后台启动openfire<br>


##### 安装mysql
```
sudo apt-get install mysql-server

```
##### 开启远程连接mysql

1. 登录mysql
```
mysql -u root -p
```
2. 授权
```
grant all privileges  on *.* to root@'%' identified by "root";
flush privileges;
```

3. 修改配置文件的ip绑定
```
sudo vim /etc/mysql/mysql.conf.d/mysqld.cnf
注释掉bind-address = 127.0.0.1
```
4. 重启服务
```
service mysql restart
```

##### mysql 中文乱码

1. 打开my.cnf文件进行修改
```
sudo vim /etc/mysql/my.cnf
```
2. 在[mysqld]下添加如下代码
```
character-set-server=utf8

collation-server=utf8_general_ci

skip-character-set-client-handshake
```


##### 安装jdk
1. 添加ppa
```
sudo add-apt-repository ppa:webupd8team/java

sudo apt-get update
```
2. 安装Oracle-java-installer
```
jdk7

sudo apt-get install oracle-java7-installer

jdk8

sudo apt-get install oracle-java8-installer

```
3. 设置系统默认jdk
```
JDk7

sudo update-java-alternatives -s java-7-oracle

JDK8

sudo update-java-alternatives -s java-8-oracle
```
4. 测试jdk 是是否安装成功:
```
java -version

javac -version
```

##### 使用FileZilla远程ubuntu上传openfire服务
1. 创建openfire文件夹并赋予权限
```
cd /usr/local
mkdir openfire
chmod 777 openfire
```
2. 使用FileZilla连接并上传

![Alt text][filezilla]

##### 后台启动openfire

```
./bin/openfire.sh &
```

##### 停止openfire

```
1. 查询openfire占用进程
netstat -tnlp
2. 杀死进程
kill -s 9 16550

```





[filezilla]:https://github.com/GepengCn/tonglian-openfire/blob/master/images/filezilla.png?raw=true
