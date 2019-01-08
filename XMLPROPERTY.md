## Openfire用户同步及数据库属性配置

<tlim>
    <xxx>
        <yyy></yyy>
    </xxx>
</tlim>

### Druid数据库连接池

- tlim.druid.maxActive:最大连接池数量
- tlim.druid.asyncInit:异步初始化
- tlim.druid.initialSize:初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
- tlim.druid.minIdle:最小连接池数量
- tlim.druid.maxWait:获取连接时最大等待时间，单位毫秒
- tlim.druid.timeBetweenEvictionRunsMillis:配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
- tlim.druid.minEvictableIdleTimeMillis:配置一个连接在池中最小生存的时间，单位是毫秒
- tlim.druid.maxEvictableIdleTimeMillis:配置一个连接在池中最大生存的时间，单位是毫秒
- tlim.druid.removeAbandoned:是否关闭未关闭的连接
- tlim.druid.removeAbandonedTimeout:未关闭连接的超市时间，单位毫秒
- tlim.druid.testWhileIdle:申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
- tlim.druid.testOnBorrow:申请连接时执行validationQuery检测连接是否有效
- tlim.druid.testOnReturn:归还连接时执行validationQuery检测连接是否有效
- tlim.druid.poolPreparedStatements:是否缓存preparedStatement
- tlim.druid.maxOpenPreparedStatements:缓存preparedStatement的最大数量


### 极光推送

- tlim.masterSecret:masterSecret
- tlim.appKey:appKey


### 用户同步

- tlim.userAsync:是否开启用户同步
- tlim.userAsyncInterval:用户同步调度周期,单位:分钟