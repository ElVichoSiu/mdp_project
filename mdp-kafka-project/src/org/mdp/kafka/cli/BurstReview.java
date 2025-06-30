// Grupo2, Integrantes: Javiera Romero Orrego, Laura Maldonado Lagos y Vicente Thiele Muñoz

package org.mdp.kafka.cli;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.mdp.kafka.def.KafkaConstants;

public class BurstReview {
	public static final int FIFO_SIZE = 10;
	public static final int EVENT_START_TIME_INTERVAL = 31536000 / 12;
	public static final int EVENT_END_TIME_INTERVAL = 2 * EVENT_START_TIME_INTERVAL;
	public final static SimpleDateFormat MOVIE_DATE = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		if(args.length!=2){
			System.err.println("Usage [inputTopic] [releaseDate]");
			return;
		}
		
		LinkedList<ConsumerRecord<String, String>> fifo = new LinkedList<ConsumerRecord<String, String>>();
		
		Properties props = KafkaConstants.PROPS;

		props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
		
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		
		consumer.subscribe(Arrays.asList(args[0]));
		
		String releaseDate = args[1];
		
		Date d = null;
		try {
			d = MOVIE_DATE.parse(releaseDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		long unixDate = d.getTime() / 1000;
		
		boolean inEvent = false;
		int events = 0;
		
		try{
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10));
				for (ConsumerRecord<String, String> record : records) {
					fifo.add(record);
					if(fifo.size()>=FIFO_SIZE) {
						ConsumerRecord<String, String> oldest = fifo.removeFirst();
						long gap = record.timestamp() - oldest.timestamp();
						if(gap <= EVENT_START_TIME_INTERVAL && !inEvent) {
							inEvent = true;
							events++;
							System.out.println("START event-id:"+ events +": start:" + oldest.timestamp() + " value:" + oldest.value() + " rate:" + FIFO_SIZE + " records in " + gap + " s");
							if(oldest.timestamp() >= unixDate && oldest.timestamp() < unixDate + 31536000 * 4) {
								System.out.println("Efecto pelicula");
							}
						} else if(gap >= EVENT_END_TIME_INTERVAL && inEvent) {
							inEvent = false;
							System.out.println("END event:" + events + " rate:" + FIFO_SIZE + " records in " + gap + " s");
							System.out.println(unixDate + " " + record.timestamp());
							if(record.timestamp() >= unixDate && record.timestamp() < unixDate + 31536000 * 4) {
								System.out.println("Efecto pelicula");
							}
						}
					}	
				}
			}
		} finally{
			consumer.close();
		}
	}
}
