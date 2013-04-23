1: 主要介绍， 怎么使用实时redis代码
   kafka， zookeeper， storm等一些基本介绍。 

2: 项目流程图参见: helper/pull_from_kafka.png

3: git clone 下来之后 
需要设置文件src/jvm/storm/product/realtime/LocalProps.java 中几个参数
\na)redis ip
\nb)kafka ips
\nc)zookeeper ips

4:kafka生产者代码
  参见 helper/producer.py
  需要实现安装:
   \n a)安装 zookpeer
        参考 http://justfansty.i.sohu.com/blog/view/217928676.htm
   \n b) 安装 samsa
        https://github.com/disqus/samsa


