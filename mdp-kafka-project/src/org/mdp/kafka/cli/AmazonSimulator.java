// Grupo2, Integrantes: Javiera Romero Orrego, Laura Maldonado Lagos y Vicente Thiele Muñoz

package org.mdp.kafka.cli;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.mdp.kafka.def.KafkaConstants;
import org.mdp.kafka.sim.ReviewStream;

public class AmazonSimulator {
	public static int REVIEW_ID = 0;
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		if(args.length!=3){
			System.err.println("Usage: [review_file_gzipped] [review_topic] [speed_up (int)]");
			return;
		}
		
		BufferedReader reviews = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
		
		String reviewTopic = args[1];
		
		int speedUp = Integer.parseInt(args[2]);
		
		Producer<String, String> reviewProducer = new KafkaProducer<String, String>(KafkaConstants.PROPS);
		ReviewStream reviewStream = new ReviewStream(reviews, REVIEW_ID, reviewProducer, reviewTopic, speedUp);
		
		Thread reviewThread = new Thread(reviewStream);
		
		reviewThread.start();
		
		try{
			reviewThread.join();
		} catch(InterruptedException e){
			System.err.println("Interrupted!");
		}
		
		reviewProducer.close();
	}
}