package com.onlinegazeta.demo.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegitrationRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
