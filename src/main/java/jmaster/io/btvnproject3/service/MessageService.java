package jmaster.io.btvnproject3.service;

import jmaster.io.btvnproject3.DTO.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private EmailService emailSrevice;

    @KafkaListener(id = "notificationGroup", topics = "notification")
    public void listen(MessageDTO messageDTO) {
        logger.info("received: " + messageDTO.getToName());
//        emailService.sendEmail(messageDTO);
    }
}
