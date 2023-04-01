package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class WhatsappService {

    WhatsappRepository whatsappRepository = new WhatsappRepository();
    public boolean newUser(String mobileNo){
        return whatsappRepository.newUser(mobileNo);
    }

    public String createUser(String name, String mobileNo){
        whatsappRepository.registerUser(name, mobileNo);
        return "SUCCESS";
    }

    public Group createGroup(List<User> userList){
        return whatsappRepository.createGroup(userList);
    }

    public int createMessage(String content){
        return whatsappRepository.createMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{
        return whatsappRepository.sendMessage(message, sender, group);
    }

    public String changeAdmin(User approver, User user, Group group) throws  Exception{
        return whatsappRepository.changeAdmin(approver, user, group);
    }
}
