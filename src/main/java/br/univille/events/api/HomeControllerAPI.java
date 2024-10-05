package br.univille.events.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class HomeControllerAPI {

    @Autowired
    @Qualifier("topicSenderClient")
    private ServiceBusSenderClient topicSenderClient;

    @Autowired
    @Qualifier("queueSenderClient")
    private ServiceBusSenderClient queueSenderClient;
    
    @Autowired
    @Qualifier("serviceBusTopicProcessorClient")
    private ServiceBusProcessorClient processorTopicClient;

    @Autowired
    @Qualifier("serviceBusQueueProcessorClient")
    private ServiceBusProcessorClient processorQueueClient;
    
    @PostMapping("/topic/send")
    public ResponseEntity topicSend(@RequestBody String message) {
        System.out.println(message);
        topicSenderClient.sendMessage(new ServiceBusMessage(message));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/queue/send")
    public ResponseEntity queueSend(@RequestBody String message) {
        System.out.println(message);
        queueSenderClient.sendMessage(new ServiceBusMessage(message));
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/topic/receive")
    public ResponseEntity topicReceive() {
        processorTopicClient.start();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/queue/receive")
    public ResponseEntity queueReceive() {
        processorQueueClient.start();
        return ResponseEntity.ok().build();
    }
}
