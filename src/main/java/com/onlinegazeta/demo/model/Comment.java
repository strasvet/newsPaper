package com.onlinegazeta.demo.model;

import com.onlinegazeta.demo.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by dsrpc on 17.02.2018.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    private String body;
    private Date createdOn;
    private User createdBy;
}
