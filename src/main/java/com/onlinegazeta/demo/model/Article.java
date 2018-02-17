package com.onlinegazeta.demo.model;

import com.onlinegazeta.demo.model.Comment;
import com.onlinegazeta.demo.model.Enum.Category;
import com.onlinegazeta.demo.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by dsrpc on 17.02.2018.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Article {
    private Integer id;
    private String title;
    private String body;
    private Date createdOn;
    private User createdBy;
    private List<Category> category;
    private List<Comment> comment;
}
