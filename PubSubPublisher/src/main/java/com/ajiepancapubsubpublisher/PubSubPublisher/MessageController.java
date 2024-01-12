package com.ajiepancapubsubpublisher.PubSubPublisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

	@Autowired
    private PubSubPublisherApplication.PubsubOutboundGateway messagingGateway;
	
	@PostMapping("/postMessage")
	public String publishMessage(@RequestBody String message) {
		messagingGateway.sendToPubsub(message);
        return "message success";
	}
}
