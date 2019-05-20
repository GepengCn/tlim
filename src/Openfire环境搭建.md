## Openfire环境搭建

### Ant

#### 1. 下载项目
> https://github.com/GepengCn/tlim.git

#### 2. 导入项目

2.1 以Intellij Idea为例


2.2 选择刚刚源码根目录进行导入

2.3 Create project from existing sources

2.4 然后一直点击next直到finish

2.5 使用Ant管理项目,选择打开build.xml

2.6 初始化需要编译项目
```
clean
compile
openfire
```
2.7 编译插件

打开build/build.properties

指定要编译的插件,执行`plugin`


2.8 运行

```
run
```
