// Grupo2, Integrantes: Javiera Romero Orrego, Laura Maldonado Lagos y Vicente Thiele Muñoz

package org.mdp.kafka.sim;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ReviewStream implements Runnable {
	BufferedReader br;
	long startSim = 0;
	long startData = 0;
	long lastData = 0;
	int speedup;
	int id;
	Producer<String, String> producer;
	String topic;
	
	public ReviewStream(BufferedReader br, int id, Producer<String, String> producer, String topic, int speedup){
		this(br,id,System.currentTimeMillis(),producer,topic,speedup);
	}
	
	public ReviewStream(BufferedReader br, int id, long startSim, Producer<String, String> producer, String topic, int speedup){
		this.br = br;
		this.id = id;
		this.startSim = startSim;
		this.producer = producer;
		this.speedup = speedup;
		this.topic = topic;
	}

	@Override
	public void run() {
		String line;
		long wait = 0;
		try{
			while((line = br.readLine())!=null){
				String[] tabs = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
				if(tabs.length>id){
					try{
						long timeData = Long.parseLong(tabs[7]);
						if(timeData >= 0) {
							if(startData == 0)
								startData = timeData;
						
							wait = calculateWait(timeData);
						
							String idStr = tabs[id];
						
							if(wait>0){
								Thread.sleep(wait);
							}
							producer.send(new ProducerRecord<String,String>(topic, 0, timeData, idStr, tabs[1]));
						}
					} catch(NumberFormatException pe){
						System.err.println("Cannot parse date "+tabs[7]);
					}
				}
				
				if (Thread.interrupted()) {
				    throw new InterruptedException();
				}
			}
		} catch(IOException ioe){
			System.err.println(ioe.getMessage());
		} catch(InterruptedException ie){
			System.err.println("Interrupted "+ie.getMessage());
		}
		
		System.err.println("Finished! Messages were "+wait+" ms from target speed-up times.");
	}

	private long calculateWait(long time) {
		long current = System.currentTimeMillis();
		
		long delaySim = current - startSim;
		if(delaySim<0){
			return delaySim*-1;
		}
		
		long delayData = time - startData;
		long shouldDelay = delayData / speedup;
		
		if(delaySim>=shouldDelay) return 0;
		else return shouldDelay - delaySim;
	}
}