#为什么写这个RPC
    最近在看dubbo的源码，但是看的过程中，遇到很多问题，一些细节的地方似懂非懂；而且dubbo的代码量很大而且，运用大量的设计模式，阅读起来很不方便；
    总结一句话：纸上得来终觉浅,绝知此事要躬行。所以自己根据dubbo的核心部分的理解实现了一个简单的RPC；
#该RPC目前的功能点
**整体设计：**
    1、与spring结合使用
    2、使用SPI技术实现，策略调度
**具体模块：**
    1、jalja-transport：
    *主要是通信相关的部分，目前只是实现的tomcat版本的http ；解下来将实现 netty 版本的http 和 netty 的tcp ；
    *动态代理，目前实现了JDK的动态代理 和 CGlib版本的动态代理
    2、jalja-register：注册中心，目前实现了 zk 和 本地文件注册
    3、jalja-common：公共模块，工具包。序列化方式也在其中，目前只是实现了 JDK版本的序列化，后期计划加上 protocol 和 hessian
    4、jalja-config：与spring整合的模块
    5、jalja-example：就是一个demo 
#基础使用
    大家参考 jalja-example 这个模块即可
#提示
    如果有看dubbo 代码像我一样有些困难的小伙伴，不妨先看看我这个简易版的RPC