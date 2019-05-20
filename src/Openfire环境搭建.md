## Openfire环境搭建

### Ant

#### 1. 下载项目
> https://github.com/GepengCn/tlim.git

#### 2. 导入项目

2.1 以Intellij Idea为例
![Alt text][oStep1]

2.2 选择源码根目录进行导入

2.3 Create project from existing sources

![Alt text][oStep2]

2.4 然后一直点击next直到finish

![Alt text][oStep3]

2.5 使用Ant管理项目,选择打开build.xml

![Alt text][oStep4]

![Alt text][oStep5]

2.6 初始化需要编译项目

![Alt text][oStep6]

```
clean
compile
openfire
```
2.7 编译插件

打开build/build.properties

指定要编译的插件,执行`plugin`

![Alt text][oStep7]

2.8 运行

```
run
```
![Alt text][oStep8]

[oStep1]:https://github.com/GepengCn/tlim/blob/master/images/oStep1.png?raw=true
[oStep2]:https://github.com/GepengCn/tlim/blob/master/images/oStep2.png?raw=true
[oStep3]:https://github.com/GepengCn/tlim/blob/master/images/oStep3.png?raw=true
[oStep4]:https://github.com/GepengCn/tlim/blob/master/images/oStep4.png?raw=true
[oStep5]:https://github.com/GepengCn/tlim/blob/master/images/oStep5.png?raw=true
[oStep6]:https://github.com/GepengCn/tlim/blob/master/images/oStep6.png?raw=true
[oStep7]:https://github.com/GepengCn/tlim/blob/master/images/oStep7.png?raw=true
[oStep8]:https://github.com/GepengCn/tlim/blob/master/images/oStep8.png?raw=true