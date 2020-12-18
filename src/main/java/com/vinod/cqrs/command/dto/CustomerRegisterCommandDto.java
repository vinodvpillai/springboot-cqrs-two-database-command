package com.vinod.cqrs.command.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegisterCommandDto implements Serializable {
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
}
