## Openfire插件式开发

### 简述

实现插件式开发可以在不该动Openfire源码的基础上，做自定义的功能。通过Openfire提供的接口，用户可以在自己的插件做消息的拦截、处理、转发等功能。除此之外，插件是可插拔的，所以插件的使用不受限于Openfire版本，可以自由选择用与不用。

### 插件开发部署

1.1 插件结构如下

![Alt text][plugin1]

1.2 定义一个插件主类，实现Plugin接口
> 实现`Plugin`接口

```java
    //插件初始化
    @Override
    public void initializePlugin(PluginManager manager, File pluginDirectory) {
    }
    //插件销毁
    @Override
    public void destroyPlugin() {
    }
```

1.3 实现`PacketInterceptor`接口

```java
    //消息拦截方法
    @Override
    public void interceptPacket(Packet packet, Session session, boolean incoming, boolean processed) throws PacketRejectedException {
    }

```

1.4 配置`plugin.xml`文件
```xml
<?xml version="1.0" encoding="UTF-8"?>

<plugin>
    <class>com.itonglian.TonglianImPlugin</class>
    <name>Instant Message Plugin for tonglian</name>
    <description>即时通讯插件</description>
    <author>Gepeng</author>
    <version>2.3.0</version>
    <date>14/01/2019</date>
    <minServerVersion>4.2.3</minServerVersion>
    <databaseKey>tlim</databaseKey>
    <databaseVersion>1</databaseVersion>

    <adminconsole>

        <tab id="tlim" name="tlim" url="index.jsp" description="tlim">
            <sidebar id="tlimbar" name="tlimbar">
                <item id="tlimPlugin" name="即时通讯插件"
                      description="同联定制" />
            </sidebar>
        </tab>

    </adminconsole>

</plugin>
```

1.5 `build/build.properties`下新增插件包名
```
plugin=tlim
```
1.6 执行`plugin` ant指令即可将插件编译并加入到openfire中了

[oStep1]:https://github.com/GepengCn/tlim/blob/master/images/plugin1.png?raw=true