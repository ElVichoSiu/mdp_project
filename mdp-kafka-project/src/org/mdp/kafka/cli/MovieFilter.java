// Grupo2, Integrantes: Javiera Romero Orrego, Laura Maldonado Lagos y Vicente Thiele Muñoz

package org.mdp.kafka.cli;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.mdp.kafka.def.KafkaConstants;

public class MovieFilter {
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		if(args.length!=3){
			System.err.println("Usage [inputTopic] [outputTopic] [bookName]");
			return;
		}
		
		Properties props = KafkaConstants.PROPS;
		props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
		
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		
		consumer.subscribe(Arrays.asList(args[0]));
		
		String bookName = args[2];
		
		Producer<String, String> producer = new KafkaProducer<String, String>(props);
		try{
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10));
				for (ConsumerRecord<String, String> record : records) {
					String lowercase = record.value().toLowerCase();
						if(lowercase.contains(bookName)){
							producer.send(new ProducerRecord<>(args[1], 0, record.timestamp(), record.key(), record.value()));
							break;
						}
					}
				}
		} finally{
			consumer.close();
			producer.close();
		}
	}
}