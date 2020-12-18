package com.vinod.cqrs.command.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCreatedEventData implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private String emailId;
    private boolean enabled;
}
