# ElasticSearchClient


这是ElasticSearch图形化客户端工具，使用的是Java SWT工具编写，基于RedisClient项目来编写了这个工具，能够编辑ES数据，添加，修改，删除，查询，剪切，复制，粘贴等等。

![My image](https://github.com/caoxinyu/RedisClient/raw/windows/src/main/resources/screen.png)

--------

## 功能

**Windows环境下的图形化界面**

**多ES版本适配**

 1. 管理ES服务器，支持服务器密码验证
 2. 管理ES文档索引
 3. 管理ES文档
 	* 新建ES文档
 	* 修改ES文档
 	* 删除ES文档 
 	* 更新ES文档
 	* 剪切，复制，粘贴ES文档
 	* 导入，导出ES文档
 	* 搜索ES文档
 	* 排序ES文档
 	* 查看历史记录
 	* Support time to live
 	* Support paging query redis data
 	* Support multiple selection to delete, cut, copy, export redis data
 	* Support flat view and hierarchy view to list redis data
 	* Support multiple language, now support English and Chinese
 	* Support run redis command in console
 	* Support run publish/subscribe
 	* Watch redis data for different format(plain text, json, xml, base64 image)


## Install & run for Windows
(For Linux, please switch [branch](https://github.com/caoxinyu/RedisClient/tree/linux). For Mac, please switch [branch](https://github.com/caoxinyu/RedisClient/tree/OSX))
### If you don't want to install JDK

1. Please download [redisclient-win32.x86.2.0.exe](https://raw.githubusercontent.com/caoxinyu/RedisClient/windows/release/redisclient-win32.x86.2.0.exe)
2. Double click redisclient-win32.x86.2.0.exe to install it to directory you choosed
3. Run it by double click redisclient-win32.x86.2.0.exe


### If you have installed the JDK or JRE 5+ 

#### For 64 bit windows
 1. Download the runable jar file [redisclient-win32.x86_64.2.0.jar](https://github.com/caoxinyu/RedisClient/blob/windows/release/redisclient-win32.x86_64.2.0.jar?raw=true)
 2. Run the redisclient-win32.x86_64.2.0.jar
 	* You can run it by double clicking it if your registry for jar file is configured correctly.
 	* Or you can run it from command line, and input `java -jar redisclient-win32.x86_64.2.0.jar`. Please pay attention to run it as administrator in windows 8.
 	
#### For 32 bit windows
 1. Download the runable jar file [redisclient-win32.x86.2.0.jar](https://github.com/caoxinyu/RedisClient/blob/windows/release/redisclient-win32.x86.2.0.jar?raw=true)
 2. Run the redisclient-win32.x86.2.0.jar
 	* You can run it by double clicking it if your registry for jar file is configured correctly.
 	* Or you can run it from command line, and input `java -jar redisclient-win32.x86.2.0.jar`. 

## Donate
 
If you find this software useful and would like to support it, you can do so simply by scanning my Alipay two-dimension code and donating whatever you like.

![My code](https://github.com/caoxinyu/RedisClient/raw/windows/src/main/resources/code.png)
 
