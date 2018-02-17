package com.onlinegazeta.demo.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserSession {
    private String sessionId;
    private User user;
    private Boolean isValid;
}
