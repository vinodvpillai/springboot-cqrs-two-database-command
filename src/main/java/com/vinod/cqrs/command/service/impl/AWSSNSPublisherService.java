package com.vinod.cqrs.command.service.impl;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.vinod.cqrs.command.service.IPublisherService;
import com.vinod.cqrs.command.util.GlobalUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AWSSNSPublisherService implements IPublisherService {

    @Autowired
    private AmazonSNSClient snsClient;

    @Override
    public <T> String publish(String topicName, T message, String subject) {
        log.trace("Request came to publish message to :{} with subject: {}",topicName,subject);
        try{
            CreateTopicResult topic = snsClient.createTopic(topicName);
            String jsonObject= GlobalUtility.convertObjectToJson(message);
            PublishRequest publishRequest=new PublishRequest(topic.getTopicArn(),jsonObject,subject);
            PublishResult publishResult=snsClient.publish(publishRequest);
            String resultMessageId=publishResult.getMessageId();
            log.debug("Successfully publish message to :{} with subject: {} result message id: {}",topicName, subject, resultMessageId);
            return resultMessageId;
        } catch (Exception exception) {
            log.error("Error occurred while publishing message to :{} with subject: {}",topicName,subject,exception);
        }
        return  null;
    }
}
