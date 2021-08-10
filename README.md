# 随机色图插件(suijisetu)

***一个基于Mirai 2.7-M2的机器人插件，用以发送随机图片***

***新增权限系统，支持跨群发送消息***

## 使用说明

* **插件项目地址 [github项目地址](https://github.com/Ycituss/suijisetu)  [Gitee项目地址](https://gitee.com/ycycycc123/suijisetu)**
* **插件基于[Mirai Clonsole](https://github.com/mamoe/mirai-clonsole) 2.7-M2版本开发，请使用Mirai-Clonsole 2.7-M2及以上版本使用本插件**
* **插件最新下载地址[github下载地址](https://github.com/Ycituss/suijisetu/releases)     [Gitee下载地址](https://gitee.com/ycycycc123/suijisetu/releases/1.0.0)**
* **插件所用API[随机色图 (lolicon.app)](https://api.lolicon.app/#/setu) 、[韩小韩API接口站](https://api.vvhan.com/)**
* **使用时将mirai.jar文件放入plugins文件夹，重启Mirai**

## 指令说明

- ### **权限管理**
>说明|指令|示例
>---|---|---
> 查看当前拥有权限的成员|super list|
> 给成员添加权限|super add QQ号|super add 2799282971
> 删除成员权限|super del QQ号|super del 2799282971
> 打开随机色图|super open setu
> 关闭随机色图|super close setu
> 打开随机色图r18功能|super open r18|
> 关闭随机色图r18功能|super close r18

- ### **随机图片指令**
><font color=Darkorange>*指令头*</font>|<font color=Darkorange>*gkd/来张图*</font>
> ---|---
> 
>说明|指令|示例
> ---|---|---
> 发送随机风景图|风景/风景图|gkd 风景
> 发送随机英雄联盟图片|lol/英雄联盟|gkd lol
> 发送随机手机分辨率美图|真人/三次元|来张图 三次元
> 发送随机电脑分辨率美图|无|gkd
> 
> <font color=DarkSalmon>*随机发送二次元色图指令*</font>|<font color=DarkSalmon>*gkd/来张图 setu/色图 (图片规格) (内容偏好)*</font>
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