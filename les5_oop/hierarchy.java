
/*
Online Java - IDE, Code Editor, Compiler

Online Java is a quick and easy tool that helps you to build, compile, test your programs online.
*/

import java.util.*;

class User
{
    int id;
    String name, forname;
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
}

class Developer extends User
{
    Manager supervisor;
    
    public Developer(int userId, Manager _supervisor)
    {
        super(userId);
        supervisor = _supervisor;
    }

    public Developer(int userId)
    {
        super(userId);
        supervisor = null;
    }
    public void setSupervisor(Manager manager)
    {
        supervisor = manager;
    }
}

class Manager extends User
{
    String duty;
    List<Developer> workers;
    
    public Manager(int userId)
    {
        super(userId);
        workers = new ArrayList<Developer>();
        workers.clear();
    }
    
    public void addWorker(Developer _worker)
    {
        workers.add(_worker);
        _worker.setSupervisor(this);
    }
    
    public void delWorker(Developer _worker)
    {
        workers.remove(_worker);
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
}

public class Main
{
    public static void main(String[] args) {
        Developer d1 = new Developer(0);
        Developer d2 = new Developer(1);
        Developer d3 = new Developer(2);
        Developer d4 = new Developer(3);
        Developer d5 = new Developer(6);
        Manager m1 = new Manager(4);
        Manager m2 = new Manager(5);
        d1.setUserName("Michail");
        d1.setForname("Silkin");
        d2.setUserName("Ivan");
        d2.setForname("Ivanov");
        d3.setUserName("Masyanya");
        d3.setForname("Kuvaeva");
        d4.setUserName("Alex");
        d4.setForname("Gyver");
        d5.setUserName("Boris");
        d5.setForname("TheBlade");
        
        m1.setUserName("Jason");
        m1.setForname("Voorhees");
        m2.setUserName("Freddy");
        m2.setForname("Krugger");
        
        m1.addWorker(d1);
        m1.addWorker(d5);
        m1.addWorker(d3);
        
        m2.addWorker(d4);
        m2.addWorker(d2);
        
        System.out.println("Log the workers:");
        
        m1.printManager();
        System.out.println("=========");
        
        m2.printManager();
    }
}
