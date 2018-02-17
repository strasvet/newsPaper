package com.onlinegazeta.demo.model;

import com.onlinegazeta.demo.model.Enum.Role;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private List<Role> roles;

}
