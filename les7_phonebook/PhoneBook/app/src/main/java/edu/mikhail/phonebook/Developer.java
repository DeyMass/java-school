package edu.mikhail.phonebook;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Developer extends User
{
    Manager supervisor;
    String supervisorID;
    
    public Developer(int userId, Manager _supervisor)
    {
        super(userId);
        setSupervisor(_supervisor);
    }

    public Developer()
    {
        super();
        supervisor = null;
    }

    public Developer(int userId)
    {
        super(userId);
        supervisor = null;
    }
    public void setSupervisor(Manager manager)
    {
        supervisor = manager;
        if (manager != null)
            supervisorID = manager.getUserId();
    }

    private List<Developer> readUserList(String filename)
    {
        ObjectMapper map = new ObjectMapper();
        List<Developer> userList;

        try {
            userList = map.readValue(new File(filename), new TypeReference<List<Developer>>() {
            });
        }
        catch (IOException exception)
        {
            System.out.println("readUserList Error: " + exception.getMessage());
            userList = new ArrayList<Developer>();
        }
        return userList;
    }

    public void saveUserData(String filename)
    {
        int i;
        ObjectMapper map = new ObjectMapper();
        System.out.println("Read before save");
        List<Developer> userList = readUserList(filename);


        System.out.println("Current list contains " + userList.size() + " record.");
        for(i = 0; i < userList.size(); i++)
        {
            userList.get(i).printUser(4);
            if (userList.get(i).getUserId().equals(String.valueOf(id)))
            {
                userList.get(i).setUserName(name);
                userList.get(i).setForname(forname);
                break;
            }
        }

        if (i == userList.size())
        {
            System.out.println("User " + name + " currently is not present. Append it");
            this.printUser(8);
            userList.add(this);
        }

        try{
            map.writerFor(this.getClass());
            map.writeValue(new File(filename), userList);
        }catch (IOException exception)
        {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    public void loadUserData(String filename, int id)
    {
        ObjectMapper map = new ObjectMapper();
        List<Developer> userList = readUserList(filename);

        for (int i = 0; i < userList.size(); i++)
        {
            if (userList.get(i).getUserId() == String.valueOf(id))
            {
                name = userList.get(i).getUserName();
                forname = userList.get(i).getForname();
                return;
            }
        }
    }

    public String getSupervisor(){ return supervisorID; }
}

