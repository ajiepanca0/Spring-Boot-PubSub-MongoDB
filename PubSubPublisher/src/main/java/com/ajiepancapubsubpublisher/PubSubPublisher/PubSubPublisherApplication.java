package com.ajiepancapubsubpublisher.PubSubPublisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;

@SpringBootApplication
public class PubSubPublisherApplication {

	public static void main(String[] args) {
		SpringApplication.run(PubSubPublisherApplication.class, args);
	}

	
	@Bean
	   @ServiceActivator(inputChannel = "myOutputChannel")
	   public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
	      return new PubSubMessageHandler(pubsubTemplate, "hello");
	   }
	
	 @MessagingGateway(defaultRequestChannel = "myOutputChannel")
	   public interface PubsubOutboundGateway {
	      void sendToPubsub(String text);
	   }

}
