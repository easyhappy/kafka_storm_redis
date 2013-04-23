#!/usr/bin/env python
#coding: utf8

from kazoo.client import KazooClient
from samsa.cluster import Cluster
import time

def current_time():
    c_time = time.time()
    return "%s\t%s" %(str(time.strftime("%Y-%M-%d %H:%m:%S", time.localtime(c_time))), c_time)

def producer(topic):
    zookeeper = KazooClient('172.16.252.201:2181', '172.16.252.202:2181', '172.16.252.203:2181')
    zookeeper.start()
    cluster = Cluster(zookeeper)
    topic = cluster.topics[topic]

    i = 0
    while(True):
        i += 1
        topic.publish("hhworld_%s::%s" %(i, current_time()))
        if i > 100:
            break

def comsumer(topic):
    zookeeper = KazooClient('172.16.252.201', 2181)
    zookeeper.start()
    cluster = Cluster(zookeeper)

    cluster.brokers.keys()
    topic = cluster.topics[topic]
    c = topic.subscribe('group-name')
    for i in c:
        print i

if __name__ == '__main__':
    topic = 'kafka_offset_11'
    producer(topic)
