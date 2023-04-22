/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package test.gradle;

import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    public String getGreeting() {
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

        d1.saveUserData("developers_list.json");
        d2.saveUserData("developers_list.json");
        d3.saveUserData("developers_list.json");
        d4.saveUserData("developers_list.json");
        d5.saveUserData("developers_list.json");

        m1.saveUserData("managers_list.json");
        m2.saveUserData("managers_list.json");
        return "Hello World 123!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}