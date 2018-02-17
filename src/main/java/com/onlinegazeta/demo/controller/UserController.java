package com.onlinegazeta.demo.controller;

import com.onlinegazeta.demo.model.Enum.Role;
import com.onlinegazeta.demo.model.User;
import com.onlinegazeta.demo.model.UserSession;
import com.onlinegazeta.demo.model.web.LoginRequest;
import com.onlinegazeta.demo.model.web.RegitrationRequest;
import com.onlinegazeta.demo.repository.UserRepository;
import com.onlinegazeta.demo.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSessionRepository userSessionRepository;

    @PostMapping("/register")
    public UserSession register(@RequestBody RegitrationRequest request){
        User user = userRepository.createUser(request);
        UserSession userSession = userSessionRepository.create(UUID.randomUUID().toString(), user);
        return userSession;
    }
    @PostMapping("/login")
        public UserSession logIn(@RequestBody LoginRequest request){
        User user = userRepository.getBuUsernameAndPassword(request.getUsername(),request.getPassword());
        if(user == null){
            throw new UsernameNotFoundException("Login is incorrect");
        }
        return userSessionRepository.create(UUID.randomUUID().toString(),user);

    }
    @PutMapping("/logout")
    public void logOut(@RequestHeader("Authorization") String sessionId){
        userSessionRepository.invalidateSession(sessionId);
    }
    @GetMapping("/getAll")
    public List<User> getAll(){
        return userRepository.getAll();
    }
    @GetMapping("/{id}")
    public User getById(@PathVariable("id") Integer userId){
        return userRepository.getByUserId(userId);
    }
    @PutMapping("/promote/{id}/{role}")
    public User promote(@PathVariable("id") Integer userId, @PathVariable("role") Role roleType){
        return userRepository.promote(userId, roleType);
    }
    @PutMapping("/demote/{id}/{role}")
    public User demote(@PathVariable("id") Integer userId, @PathVariable("role") Role roleType){
        return userRepository.demote(userId, roleType);
    }
}
