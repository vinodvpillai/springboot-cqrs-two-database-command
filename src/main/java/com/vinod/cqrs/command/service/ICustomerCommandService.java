package com.vinod.cqrs.command.service;

import com.vinod.cqrs.command.dto.CustomerRegisterCommandDto;

public interface ICustomerCommandService {

    /**
     * Add customer object to database.
     *
     * @param customerRegisterCommandDto - Customer register command object.
     */
    void addCustomerAndRaiseEvent(CustomerRegisterCommandDto customerRegisterCommandDto);
}
