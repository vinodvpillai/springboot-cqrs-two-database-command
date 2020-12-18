package com.vinod.cqrs.command.service.impl;

import com.vinod.cqrs.command.dto.CustomerRegisterCommandDto;
import com.vinod.cqrs.command.event.CustomerCreatedEvent;
import com.vinod.cqrs.command.event.CustomerCreatedEventData;
import com.vinod.cqrs.command.model.Customer;
import com.vinod.cqrs.command.repository.CustomerRepository;
import com.vinod.cqrs.command.service.ICustomerCommandService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.vinod.cqrs.command.util.GlobalUtility.formatCustomerId;

@Service
@Slf4j
public class CustomerCommandService implements ICustomerCommandService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CustomerCreatedEvent customerCreatedEvent;

    /**
     * Add customer object to database and raise event.
     *
     * @param customerRegisterCommandDto - Customer register command object.
     */
    @Override
    public void addCustomerAndRaiseEvent(CustomerRegisterCommandDto customerRegisterCommandDto) {
        log.trace("Request came to add new customer : {}", customerRegisterCommandDto);
        Customer customer = saveCustomer(customerRegisterCommandDto);
        raiseCustomerCreatedEvent(customer);
        log.trace("Successfully saved customer object for: {}", customerRegisterCommandDto);
    }

    /**
     * Save the customer object to database.
     *
     * @param customerRegisterCommandDto - Customer register command dto object.
     * @return  - Customer object.
     */
    private Customer saveCustomer(CustomerRegisterCommandDto customerRegisterCommandDto) {
        log.trace("Request came to save the customer object with customer details: {}", customerRegisterCommandDto);
        Customer customer = mapDataToCustomer(customerRegisterCommandDto);
        customerRepository.save(customer);
        return customer;
    }

    /**
     * Raise the customer created event.
     *
     * @param customer - Customer object.
     */
    private void raiseCustomerCreatedEvent(Customer customer) {
        log.trace("Request came to raise event for customer created event for customer: {}", customer);
        CustomerCreatedEventData customerCreatedEventData = mapDataToCustomerCreatedEventData(customer);
        customerCreatedEvent.on(customerCreatedEventData);
    }

    /**
     * Map CustomerRegisterCommandDto to Customer object.
     *
     * @param customerRegisterCommandDto - CustomerRegisterCommandDto object.
     * @return  - Customer object.
     */
    private Customer mapDataToCustomer(CustomerRegisterCommandDto customerRegisterCommandDto) {
        modelMapper.typeMap(CustomerRegisterCommandDto.class, Customer.class).addMappings(mapper -> mapper.skip(Customer::setId));
        Customer customer = modelMapper.map(customerRegisterCommandDto, Customer.class);
        customer.setEnabled(true);
        return customer;
    }

    /**
     * Map CustomerRegisterCommandDto to Customer object.
     *
     * @param customer - CustomerRegisterCommandDto object.
     * @return  - Customer object.
     */
    private CustomerCreatedEventData mapDataToCustomerCreatedEventData(Customer customer) {
        modelMapper.typeMap(Customer.class, CustomerCreatedEventData.class).addMappings(mapper -> mapper.skip(CustomerCreatedEventData::setId));
        CustomerCreatedEventData customerCreatedEventData = modelMapper.map(customer, CustomerCreatedEventData.class);
        customerCreatedEventData.setId(formatCustomerId(String.valueOf(customer.getId()),'C',8));
        return customerCreatedEventData;
    }
}
