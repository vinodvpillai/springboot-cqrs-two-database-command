package com.vinod.cqrs.command.event;

import com.vinod.cqrs.command.service.IPublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerCreatedEvent {
    @Autowired
    private IPublisherService publisherEventService;

    @Value("${sns.topic.customer.created}")
    private String snsCustomerCreated;

    public void on(CustomerCreatedEventData customer) {
        log.trace("Request came to raise event for customer created: {}",customer);
        try{
            publisherEventService.publish(snsCustomerCreated,customer,"Customer_Created");
            log.info("Successfully publish message for customer created: {}",customer);
        } catch (Exception e) {
            log.error("Error occurred while raising event for customer created: {}",customer);
        }
    }

}
