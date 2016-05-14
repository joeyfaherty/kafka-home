package main.java.messaging.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by joey on 5/14/16.
 */
public class Consumer {

    private static boolean running = true;

    public static void main(String[] args) throws IOException {

        // and the consumer, with minimal config needed to use consumer groups
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // subscribe to the topics
        consumer.subscribe(Arrays.asList("test"));

        // basic poll loop. prints the partition, offset and value of fetched records as they arrive
        try {
            while (running) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    HashMap<String, Object> data = new HashMap();
                    data.put("partition", record.partition());
                    data.put("offset", record.offset());
                    data.put("value", record.value());
                    System.out.println(data);
                }
            }
        } finally {
            System.out.println("Closing consumer");
            consumer.close();
        }
    }
}
