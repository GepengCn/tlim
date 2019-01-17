## Openfire用户同步及数据库属性配置

> 配置在文件`./conf/openfire.xml`的`<jive>`标签内

## 例如

```xml
<tlim>
    <druid>
        <maxActive>200</maxActive>
    </druid>
</tlim>
```

### Openfire双端同步

##### `tlim.nettyServer`
是否开启netty服务端
> 默认:false

##### `tlim.nettyServerPort`
netty服务端端口
> 默认:9599


##### `tlim.nettyClient`
是否开启netty客户端
> 默认:false

##### `tlim.nettyClientIp`
netty客户端ip
> 默认:127.0.0.1

##### `tlim.nettyClientPort`
netty客户端端口
> 默认:9599

### Druid数据库连接池

##### `tlim.druid.maxActive`
最大连接池数量
> 默认:500
##### `tlim.druid.asyncInit`
异步初始化
> 默认:true
    
##### `tlim.druid.initialSize` 
初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
> 默认:100

##### `tlim.druid.minIdle` 
最小连接池数量
> 默认:80

##### `tlim.druid.maxWait` 
获取连接时最大等待时间，单位毫秒
> 默认:10000

##### `tlim.druid.timeBetweenEvictionRunsMillis` 
配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
> 默认:30000

##### `tlim.druid.minEvictableIdleTimeMillis` 
配置一个连接在池中最小生存的时间，单位是毫秒
> 默认:300000

##### `tlim.druid.maxEvictableIdleTimeMillis` 
配置一个连接在池中最大生存的时间，单位是毫秒
> 默认:600000

##### `tlim.druid.removeAbandoned` 
是否关闭未关闭的连接
> 默认:true

##### `tlim.druid.removeAbandonedTimeout` 
未关闭连接的超市时间，单位毫秒
> 默认:80

##### `tlim.druid.testWhileIdle` 
申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
> 默认:true

##### `tlim.druid.testOnBorrow` 
申请连接时执行validationQuery检测连接是否有效
> 默认:false

##### `tlim.druid.testOnReturn` 
归还连接时执行validationQuery检测连接是否有效
> 默认:false

##### `tlim.druid.poolPreparedStatements` 
是否缓存preparedStatement
> 默认:true
##### `tlim.druid.maxOpenPreparedStatements` 
缓存preparedStatement的最大数量
> 默认:50



### 极光推送

##### `tlim.jpush`
是否开启极光推送

##### `tlim.masterSecret`
密钥

##### `tlim.appKey`
key

##### `tlim.threadPoolSize`
线程池数量

### 用户同步

##### `tlim.userAsync`
是否开启用户同步
> 默认:true

##### `tlim.userAsyncInterval`
用户同步调度周期,单位:分钟
> 默认:1

