# smart-framework
=====================<br>
2017年11月24日 11:27:36<br>
框架探险--从0开始写JavaWeb框架

## step first
加入一些第三方开源项目<br>
smart-framework写框架代码<br>
chapter写项目实例<br>
参考 http://blog.csdn.net/mr_zhuqiang/article/details/49256847

## step second
编写utils、helper、bean等，以及mvc最核心的DispatcherServlet，完成Ioc部分<br>
测试成功 现会对所以资源拦截 做到请求转发功能

## step third
编写aop 使用代理<br>
cglib的代理学习 https://www.cnblogs.com/cenyu/p/6289209.html<br>
用jdk的动态代理 代理对象不需要实现接口,但是目标对象一定要实现接口,否则不能用动态代理<br>
cglib代理:也叫作子类代理,它是在内存中构建一个子类对象从而实现对目标对象功能的扩展<br>