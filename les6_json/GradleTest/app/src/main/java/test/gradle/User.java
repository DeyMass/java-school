package test.gradle;

import java.util.*;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

class User
{
    int id;
    String name, forname;
    public User()
    {
        id = -1;
        name = "Unnamed";
        forname = "";
    }

    public User(int userId)
    {
        id = userId;
        name = "Unnamed";
        forname = "";
    }
    
    public void printUser(int padding)
    {
        for (int i = 0; i < padding; i++)
			System.out.print(" ");

        System.out.println(id + "|" + forname + "|" + name);
    }

    private List<User> readUserList(String filename)
    {
        ObjectMapper map = new ObjectMapper();
        List<User> userList;

        try {
            userList = map.readValue(new File(filename), new TypeReference<List<User>>() {
            });
        }
        catch (IOException exception)
        {
            System.out.println("readUserList Error: " + exception.getMessage());
            userList = new ArrayList<User>();
        }
        return userList;
    }

    public void saveUserData(String filename)
    {
        int i;
        ObjectMapper map = new ObjectMapper();
        System.out.println("Read before save");
        List<User> userList = readUserList(filename);


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
        List<User> userList = readUserList(filename);

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

    public void setUserId(int newId)
    {
        id = newId;
    }

    public void setUserName(String userName)
    {
        name = userName;
    }

    public void setForname(String userForname)
    {
        forname = userForname;
    }

    public String getUserId()
    {
        return String.valueOf(id);
    }

    public String getUserName()
    {
        return name;
    }

    public String getForname()
    {
        return forname;
    }
}
