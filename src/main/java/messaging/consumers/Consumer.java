package messaging.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.BasicUser;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by joey on 5/14/16.
 */
public class Consumer {

    private static boolean running = true;
    private static final String topic = "test";

    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        // and the consumer, with minimal config needed to use consumer groups
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // rather than subscribing to the topic, the consumer just subscribes to particular partitions
        TopicPartition partition0 = new TopicPartition(topic, 0);
        TopicPartition partition1 = new TopicPartition(topic, 1);
        consumer.assign(Arrays.asList(partition0, partition1));
        System.out.println("Subscribed to topic " + consumer.assignment());

        // subscribe to the topics
        // consumer.subscribe(Arrays.asList("test"));

        // basic poll loop. prints the partition, offset and value of fetched records as they arrive
        try {
            while (running) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    // populate the BasicUser pojo
                    BasicUser user = mapper.readValue(record.value(), BasicUser.class);

                    HashMap<String, Object> data = new HashMap();
                    data.put("partition", record.partition());
                    data.put("offset", record.offset());
                    data.put("name", user.getName());
                    data.put("age", user.getAge());
                    System.out.println(data);
                }
            }
        } finally {
            System.out.println("Closing consumer");
            consumer.close();
        }
    }
}
