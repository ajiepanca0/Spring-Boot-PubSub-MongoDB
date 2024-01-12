package com.ajiepancapubsubreceiver.PubSubReceiver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

import com.ajiepancapubsubreceiver.PubSubReceiver.Process.PubSubReceiverProcess;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.gson.Gson;


@SpringBootApplication
public class PubSubReceiverApplication {

	
	private static final Log Logger = LogFactory.getLog(PubSubReceiverApplication.class);
	
	@Autowired
	PubSubReceiverProcess pubSubReceiverProcess;
	
	
	public static void main(String[] args) {
		SpringApplication.run(PubSubReceiverApplication.class, args);
	}
	
	@Bean
	public PubSubInboundChannelAdapter messageChannelAdapter(
			@Qualifier("myInputChannel") MessageChannel inputChannel,
			PubSubTemplate pubSubTemplate) {
			PubSubInboundChannelAdapter adapter =
					new PubSubInboundChannelAdapter(pubSubTemplate, "hello-sub");
			adapter.setOutputChannel(inputChannel);
			
			return adapter;

	}
	
	@Bean
	public MessageChannel myInputChannel() {
		return new DirectChannel();
	}
	
	@ServiceActivator(inputChannel = "myInputChannel")
	public void messageReceiver(String payload) {
		
        Logger.info("message Start ========= ");
        
        PubSubDataModel data = new Gson().fromJson(payload, PubSubDataModel.class);
        pubSubReceiverProcess.insertDataToMongo(data);
        
        Logger.info("====== End Message");


		
	}
	
}
	
	
