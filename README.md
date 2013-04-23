1: 主要介绍， 怎么使用实时redis代码
   kafka， zookeeper， storm等一些基本介绍。 

2: 项目流程图参见: helper/pull_from_kafka.png

3: git clone 下来之后 
需要设置文件src/jvm/storm/product/realtime/LocalProps.java 中几个参数
<br/>a)redis ip
<br/>b)kafka ips
<br/>c)zookeeper ips

4:kafka生产者代码
  参见 helper/producer.py
  需要实现安装:
   <br/> a)安装 zookpeer
        参考 http://justfansty.i.sohu.com/blog/view/217928676.htm
   <br/> b) 安装 samsa
        https://github.com/disqus/samsa

5: 相关命令
a) 安装本地的 jedis 驱动包： 目的是修改了jedis驱动包源码， 使得能够使用sentinel
 <br/>mvn install:install-file -Dfile=../jedis-2.0.0.ddbeta.jar -DgroupId=redis.clients -DartifactId=jedis -Dversion=2.0.0 -Dpackaging=jar 

b)将java文件 打成jar包
<br/>mvn -f m2-pom.xml package

c)启动storm 代码
</br>storm jar target/storm-product-0.1-jar-with-dependencies.jar realtime.MainTopology storm_product 


其他 参见: helper/实时redis相关总结.docx


