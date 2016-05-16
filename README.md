# kafka-home

1: download latest kafka & untar
https://www.apache.org/dyn/closer.cgi?path=/kafka/0.9.0.0/kafka_2.11-0.9.0.0.tgz

``` tar -xzf kafka_2.11-0.9.0.0.tgz ```

``` cd kafka_2.11-0.9.0.0 ```

2: start the zookeeper server and kafka server

``` bin/zookeeper-server-start.sh config/zookeeper.properties ```

``` bin/kafka-server-start.sh config/server.properties ```

3: create a topic with single partition and only one replica

``` bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test ```

4: list all topics

``` bin/kafka-topics.sh --list --zookeeper localhost:2181 ```

5: send some messages

``` bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test ```

[input some text, each line is a new msg by default]

6: Run main java class (Consumer.java) which subscribes to the "test" topic created above to consume the messages sent in the previous step.

ref: 
http://kafka.apache.org/documentation.html#quickstart
