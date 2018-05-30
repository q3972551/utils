# utils
常用工具


这里有很多项目中常用的工具类，包括对redis的封装，还有比较好用的是idWorker

idWorker类主要是用于生成唯一id的，生成的是long型。据说是推特开发出来的。
基于idWoker需要传两个值，一个是模块，一个是数据中心id。因此这里吧模块给规范了，在WorkLeader 类里
