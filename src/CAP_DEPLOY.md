### Openfire部署

- 下载
```
https://github.com/GepengCn/tlim/releases/download/v1.2/openfire_V_1_2.zip
```
- 数据库

与应用服务数据库一致
- 清理旧数据

删除`of`开头的表,如:`ofsmessage`

- Openfire初始化

启动./bin/openfire.bat成功后,浏览器访问`http://localhost:9090`

- - 语言选择

![Alt text][openfire_step_01]
- - 服务器选择

![Alt text][openfire_step_02]
- - 数据库设置

![Alt text][openfire_step_03]

![Alt text][openfire_step_04]

- - 外形设置

![Alt text][openfire_step_05]
- - 管理员账户

![Alt text][openfire_step_06]
- - 重启服务

1. 重启`Openfire`服务<br>
2. 浏览器访问http://localhost:9595
3. 使用用户名admin,密码admin登录
4. 如果登录失败,查询ofuser表`select * from ofuser where username = 'admin'`是否有记录，没有则执行
```
INSERT INTO `ofuser` (`username`, `storedKey`, `serverKey`, `salt`, `iterations`, `plainPassword`, `encryptedPassword`, `name`, `email`, `creationDate`, `modificationDate`) VALUES ('admin', NULL, NULL, NULL, NULL, 'admin', NULL, 'Administrator', 'admin@example.com', '0', '0');
```
5. 登录成功则如下显示

![Alt text][openfire_step_07]

### cap应用部署

- 配置文件

- - jdbc.properties
必须配置好数据库与openfire一致
- - 关联jar包
`cap-msg.jar`
- - 云吧
`views/aco/webim`
- - 后台配置
> 系统配置优先级：数据库记录>配置文件。<br>
> 所以配置好`/config/base/microserviceconfig.xml`后需`superadmin`登录后台,打开系统配置,检验openfire配置是否正确。<br>




[openfire_step_01]:https://github.com/GepengCn/tlim/blob/dev/images/openfire_step_01.png?raw=true
[openfire_step_02]:https://github.com/GepengCn/tlim/blob/dev/images/openfire_step_02.png?raw=true
[openfire_step_03]:https://github.com/GepengCn/tlim/blob/dev/images/openfire_step_03.png?raw=true
[openfire_step_04]:https://github.com/GepengCn/tlim/blob/dev/images/openfire_step_04.png?raw=true
[openfire_step_05]:https://github.com/GepengCn/tlim/blob/dev/images/openfire_step_05.png?raw=true
[openfire_step_06]:https://github.com/GepengCn/tlim/blob/dev/images/openfire_step_06.png?raw=true
[openfire_step_07]:https://github.com/GepengCn/tlim/blob/dev/images/openfire_step_07.png?raw=true
