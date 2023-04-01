package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashMap<String, User> userMobile;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashMap<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public boolean newUser(String mobile){
        if(userMobile.containsKey(mobile)){
            return false;
        }
        else{
            return true;
        }
    }

    public void registerUser(String name, String mobileNo){
        userMobile.put(mobileNo, new User(name, mobileNo));
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{
        if(!groupUserMap.containsKey(group)){
            throw new Exception("Group not exist");
        }

        if(!adminMap.get(group).equals(approver)){
            throw new Exception("User cannot be accepted");
        }

        if(!this.userExistsInGroup(group, user)){
            throw new Exception("User no present");
        }

        adminMap.put(group, user);

        return "SUCCESS";
    }

    public boolean userExistsInGroup(Group group, User sender){
        List<User> userList = groupUserMap.get(group);
        for(User user : userList){
            if(user.equals(sender)){
                return true;
            }
        }

        return false;
    }

    public Group createGroup(List<User> userList){
        if(userList.size() == 2){
            return this.createPersonalChat(userList);
        }

        this.customGroupCount++;
        String GroupName = "Group" + this.customGroupCount;
        Group group = new Group(GroupName, userList.size());
        groupUserMap.put(group, userList);
        adminMap.put(group, userList.get(0));

        return group;
    }

    public  Group createPersonalChat(List<User> userList){
        String groupName = userList.get(1).getName();
        Group personalGroup = new Group(groupName, 2);
        groupUserMap.put(personalGroup, userList);

        return personalGroup;
    }

    public int createMessage(String content){
        this.messageId++;
        Message message1 = new Message(messageId, content, new Date());

        return this.messageId;
    }

    public int sendMessage(Message massage, User sender, Group group) throws Exception{
        if(!groupUserMap.containsKey(group)){
            throw new Exception("Group not exist");
        }

        if(!this.userExistsInGroup(group, sender)){
            throw new Exception("You are not allowed to send message");
        }
            List<Message> messages = new ArrayList<>();
            if(groupMessageMap.containsKey(group)){
                messages = groupMessageMap.get(group);
            }

            messages.add(massage);
            groupMessageMap.put(group, messages);
            return messages.size();
    }

}
