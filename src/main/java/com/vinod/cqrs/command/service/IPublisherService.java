package com.vinod.cqrs.command.service;

public interface IPublisherService {

    <T> String publish(String topic, T msg, String subject);
}
