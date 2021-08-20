# 随机色图插件(suijisetu)

***一个基于Mirai 2.7-M2的机器人插件，用以发送随机图片***

***新增权限系统，支持跨群发送消息***

## 使用说明

* **插件项目地址 [github项目地址](https://github.com/Ycituss/suijisetu)  [Gitee项目地址](https://gitee.com/ycycycc123/suijisetu)**
* **插件基于[Mirai Clonsole](https://github.com/mamoe/mirai-clonsole) 2.7-M2版本开发，请使用Mirai-Clonsole 2.7-M2及以上版本使用本插件**
* **插件最新下载地址[github下载地址](https://github.com/Ycituss/suijisetu/releases)     [Gitee下载地址](https://gitee.com/ycycycc123/suijisetu/releases/1.0.0)**
* **插件所用API[随机色图 (lolicon.app)](https://api.lolicon.app/#/setu) 、[韩小韩API接口站](https://api.vvhan.com/)**
* **使用时将mirai.jar文件放入plugins文件夹，重启Mirai**
* **配置文件位于config文件夹下的Setu文件夹中**
* **第一次用在配置文件里手动更改botAdministrators项，改为自己的QQ号**
* **<font color=red>3.0.0版本对配置文件进行结构调整,低版本升级请删除原来的配置文件;</font>3.0.0之后的版本即可在版本更新时自动更新配置文件.**

## 指令说明

**现在可以在配置文件中更改不同命令的指令头，更改后使用`seturebot`生效**

- ### **权限管理**
>说明|指令|备注
>---|---|---
> 查看当前拥有权限的成员|super list|
> 给成员添加权限|super add QQ号|
> 删除成员权限|super del QQ号|
> 打开/关闭色图自动撤回|super open/close recall|此功能默认关闭
> 设置撤回延时|super set recallDelay 5000|延时单位为ms，默认设置为5000ms，可设置最大值为150000ms(2分半) 
> 设置默认色图画质|super set imageQuality 2|可选参数有0,1,2,3,4;初始设置为2
> 打开本群随机图片发送|super open (all)|(all)为可选参数，表示全局开启或关闭，使用时请不要带括号
> 关闭本群随机图片发送|super close (all)
> 打开本群随机色图|super open setu (all)
> 关闭本群随机色图|super close setu (all)
> 打开本群随机色图r18功能|super open r18 (all)|
> 关闭本群随机色图r18功能|super close r18 (all)

- ### **随机图片指令**
><font color=Darkorange>*指令头*</font>|<font color=Darkorange>*gkd/来张图*</font>
> ---|---
> 
> 新增指令头自定义,可通过
> <font color=Darkblu>super  command  add/del  [自定义指令头]</font>
> 更改指令头
> 
> 使用
> <font color=Darkblu>super  command  list</font>
> 查看当前指令头
> 
>说明|指令|示例
> ---|---|---
> 发送随机风景图|风景/风景图|gkd 风景
> 发送随机英雄联盟图片|lol/英雄联盟|gkd lol
> 发送随机手机分辨率美图|真人/三次元|来张图 三次元
> 发送随机电脑分辨率美图|无|gkd
> 
> <font color=DarkSalmon>*随机发送二次元色图指令*</font>|<font color=DarkSalmon>*[指令头] setu/色图 (图片规格) (内容偏好)*</font>
> ---|---
> 
> ()内为可选参数
> 
> 参数说明|可选参数|
> ---|---
> 图片规格|0,1,2,3,4
> 内容偏好|r18,R18
> 
> 示例|说明
> ---|---
> gkd setu 2|发送大小为2的随机图片
> gkd setu 3 r18|发送大小为3的r18图片

- ### **跨群发送消息**
>指令格式|指令头 群编号 要发送的消息
> ---|---
> 
> 指令头|stg,sendtogroup
> ---|---
> 群编号|通过群列表指令查看
> 示例|stg 0 test message

- ### **查看群列表**
>说明|指令
>---|---
> 列出当前机器人添加的所有群|gl,GL,grouplist,GroupList,群列表