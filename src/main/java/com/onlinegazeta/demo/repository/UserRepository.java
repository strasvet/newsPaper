package com.onlinegazeta.demo.repository;

import com.onlinegazeta.demo.model.Enum.Role;
import com.onlinegazeta.demo.model.User;
import com.onlinegazeta.demo.model.web.RegitrationRequest;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.io.NotActiveException;
import java.security.acl.NotOwnerException;
import java.util.*;


@Repository
public class UserRepository {
    //Set<User> users;


    private static Map<String,User> users = new HashMap<>();
    //username:UserObject
    private static List<User> admins = new LinkedList<>();
    //private static Map<String,User> admins = new HashMap<>();

    //register
    public User createUser(RegitrationRequest u){
        //System.err.println("ENTER TO METHOD!");

        User mapUser = users.get(u.getUsername());

        //if(mapUser==null) System.err.println("MAP==USer NOT FOUND - NULLL!!!!!!!!!!!!!!");
        if(mapUser != null){
            throw new RuntimeException(
                    String.format("Users with username already exists", u.getUsername()));
                   // "ERRORRRRRRRRRRRRRRRRRRRRRR");
        }

        List<Role> rolesLis= new LinkedList<>();
        if(users.isEmpty()) {
            rolesLis.add(Role.ADMIN);
        }else {
            rolesLis.add(Role.READER);
        }
        User user = User.builder()
                .id(users.size()+1)
                .username(u.getUsername())
                .password(u.getPassword())
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .roles(rolesLis)
                .build();
        users.putIfAbsent(user.getUsername(), user);
        //add to Admin List
        if(user.getRoles().stream().filter(x->x.equals(Role.ADMIN)).findFirst().orElse(null) !=null) admins.add(user);
        System.out.println("admins size = "+admins.size());
        //System.out.println(user);
        //putIfAbsent == null esli polozil, user esli bil. chto est v mape to i vozvrashyaet.
        //User userFromMap = users.putIfAbsent(user.getUsername(), user);
        //if(userFromMap!=null){ throw new RuntimeException(String.format("Users with username already exists", u.getUsername())); }
        return user;
    }


    public User getBuUsernameAndPassword(@NonNull String username,@NonNull String password){
        //User user =users.getOrDefault(username,null);
        User user =users.get(username);
        if(user ==null)return null;
        if(user.getPassword().equals(password)) return user;
        return null;
    }
    public User getByUsername(@NonNull String username){
        //User user =users.getOrDefault(username,null);
        //***
        //User user =users.get(username);
        //if(user!=null)return user;
        return users.get(username);
    }
    public User getByUserId(Integer id){
        return users.values().stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);
        //return null;
    }

    public List<User> getAll() {
        return new ArrayList<>(users.values());
        //Collections.enumeration(users.values());
    }

    public User promote(Integer userId, Role roleType) {
        //Reader Writer Admin
        List<Role> rolesL=new LinkedList<>();
        for (int i=0; i<=roleType.ordinal();i++){
            rolesL.add(Role.getById(i));
        }
        User user = getByUserId(userId);
        user.setRoles(rolesL);
        /*
        User user = getByUserId(userId);
        Role userRoles = user.getRoles().stream().filter(x->x.getRole().equals(roleType.getRole())).findFirst().orElse(null);
        if(userRoles==null){
            user.getRoles().add(roleType);
        }else{
            System.out.println("User already have this role");
        }*/
        //for(Role roleV:user.getRoles()){if(roleV.getRole().equals(roleType)){}}
        //Add to admin List
        if(user.getRoles().stream().filter(x->x.equals(Role.ADMIN)).findFirst().orElse(null) !=null) admins.add(user);
        System.out.println("admins size = "+admins.size());
        return user;
    }

    public User demote(Integer userId, Role roleType) {
        User user = getByUserId(userId);
        int r1 = roleType.ordinal();
        System.out.println("ordinal 1 = " + r1);
        int r2 = Role.ADMIN.ordinal();
        System.out.println("ordinal 2 = "+r2);
        System.out.println("admins size before demote = "+admins.size());
        if(r1<r2 && admins.size()==1){
            System.out.println("You dont can to remove of last admin");
            return user;
        }

        List<Role> rolesL=new LinkedList<>();
        for (int i=0; i<=roleType.ordinal();i++){
            rolesL.add(Role.getById(i));
        }
        admins.remove(user);
        System.out.println("admins size after demote = "+admins.size());
        user.setRoles(rolesL);

        /*
        if(user.getRoles().remove(roleType)){
            return user;
        }else{
            System.out.println("This role "+roleType.getRole()+" is not found in user roles");
        }*/

        /*List<Role> rolesOld = user.getRoles();
        rolesOld.remove(roleType);
        for(Role roleV:user.getRoles()){
            if(roleV.getRole().equals(roleType)) roleV = null;
            //user.getRoles().clear();
        }*/
        return user;
    }
}


