#!/usr/bin/env python
#coding:utf8
import time
import logging

from kafka.client import KafkaClient, FetchRequest, ProduceRequest

def produce_example(kafka, topic, number):
    text = "qq_%s::%s" %(number, number)
    print text
    message = kafka.create_message(text)
    request = ProduceRequest(topic,  -1,  [message])
    #kafka.send_messages_simple("my-topic", "some message")
    kafka.send_message_set(request)
    print 'test'

def consume_example(kafka, topic):
    request = FetchRequest("my-topic", 1, 0, 1024)
    (messages, nextRequest) = kafka.get_message_set(request)
    for message in messages:
        print("Got Message: %s" % (message,))
    print(nextRequest)

def produce_gz_example(kafka, topic):
    message = kafka.create_gzip_message("this message was gzipped", "along with this one")
    request = ProduceRequest(topic, 0, [message])
    kafka.send_message_set(request)

def main():
    kafka = KafkaClient('172.16.252.61', 9092)
    print kafka
    topic = 'test_kafka_4'
    number = 1100
    count = 0
    while(True):
        number += 1
        raw_input('next--->>')
        produce_example(kafka, topic, number)
        count += 1
        if count > 100:break
        #break
    #produce_example(kafka, topic, number)
    #produce_gz_example(kafka)
    #consume_example(kafka, topic)
    kafka.close()

if __name__ == "__main__":
    #logging.basicConfig(level=logging.DEBUG)
    main()
