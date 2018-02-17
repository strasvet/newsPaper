package com.onlinegazeta.demo.repository;

import com.onlinegazeta.demo.model.User;
import com.onlinegazeta.demo.model.UserSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserSessionRepository {
    private static Map<String,UserSession> sessions = new HashMap<>();
    //sessionId:UserSession


    public UserSession create(String sessionId, User user){
        UserSession userSession = UserSession.builder()
                .sessionId(sessionId)
                .user(user)
                .isValid(true)
                .build();
        sessions.putIfAbsent(sessionId,userSession);
        return userSession;
    }
    public UserSession getBySessinId(String sessionId){
        //proverka na valid invalidate
        UserSession userSession = sessions.get(sessionId);
        if(userSession==null || !userSession.getIsValid())return null;
        return userSession;
    }
    public void invalidateSession(String sessionId){
       // UserSession userSession = sessions.get(sessionId);
        UserSession userSession = sessions.get(sessionId);
        if(userSession == null) return;
        userSession.setIsValid(false);

        //return false;
    }
}
