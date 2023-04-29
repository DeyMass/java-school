package edu.mikhail.phonebook;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Manager  extends User
{
    String duty;
    List<Developer> workers;
    List<String> workersIDs;

    private void initLists()
    {
        workers = new ArrayList<Developer>();
        workersIDs = new ArrayList<String>();
        workers.clear();
        workersIDs.clear();
    }

    public Manager()
    {
        super();
        initLists();
    }

    public Manager(int userId)
    {
        super(userId);
        initLists();
    }
    
    public void addWorker(Developer _worker)
    {
        workers.add(_worker);
        workersIDs.add(_worker.getUserId());
        _worker.setSupervisor(this);
    }
    
    public void delWorker(Developer _worker)
    {
        workers.remove(_worker);
        workersIDs.remove(_worker.getUserId());
        _worker.setSupervisor(null);
    }
    
    public void printManager()
    {
        System.out.printf("Manager %s %s has %d people in control:\n", name, forname, workers.size());
        
        for (int i = 0 ; i < workers.size(); i++)
        {
            workers.get(i).printUser(4);
        }
    }

    private List<Manager> readUserList(String filename)
    {
        ObjectMapper map = new ObjectMapper();
        List<Manager> userList;

        try {
            userList = map.readValue(new File(filename), new TypeReference<List<Manager>>() {
            });
        }
        catch (IOException exception)
        {
            System.out.println("readUserList Error: " + exception.getMessage());
            userList = new ArrayList<Manager>();
        }
        return userList;
    }

    public void saveUserData(String filename)
    {
        int i;
        ObjectMapper map = new ObjectMapper();
        System.out.println("Read before save");
        List<Manager> userList = readUserList(filename);


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

    public void loadUserData(String filename, int id) {
        List<Manager> userList = readUserList(filename);

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId() == String.valueOf(id)) {
                name = userList.get(i).getUserName();
                forname = userList.get(i).getForname();
                return;
            }
        }
    }

    public List<String> getWorkers()
    {
        return workersIDs;
    }
}

