package edu.mikhail.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    User [] userList;

    private void createUserList()
    {
        Developer d1 = new Developer(0);
        Developer d2 = new Developer(1);
        Developer d3 = new Developer(2);
        Developer d4 = new Developer(3);
        Developer d5 = new Developer(6);
        Manager m1 = new Manager(4);
        Manager m2 = new Manager(5);
        d1.setUserName("Michail");
        d1.setForname("Silkin");
        d1.setNumber("88005553535");

        d2.setUserName("Ivan");
        d2.setForname("Ivanov");
        d2.setNumber("123456789");

        d3.setUserName("Masyanya");
        d3.setForname("Kuvaeva");
        d3.setNumber("777555333");

        d4.setUserName("Alex");
        d4.setForname("Gyver");
        d4.setNumber("11122233");

        d5.setUserName("Boris");
        d5.setForname("TheBlade");
        d5.setNumber("78797978767");

        m1.setUserName("Jason");
        m1.setForname("Voorhees");
        m1.setNumber("123212313");

        m2.setUserName("Freddy");
        m2.setForname("Krugger");
        m2.setNumber("66644455566");

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

        userList = new User[7];
        userList[0] = d1;
        userList[1] = d2;
        userList[2] = d3;
        userList[3] = d4;
        userList[4] = d5;
        userList[5] = m1;
        userList[6] = m2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView lv;

        createUserList();

        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.listView);

        lv.setAdapter(new CustomListAdapter(this, userList));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView number = view.findViewById(R.id.row_phone);
                TextView name = view.findViewById(R.id.row_name);
                TextView group = view.findViewById(R.id.row_group);

                Log.d("Click", "Item clicked: " +
                        name.getText() +
                        " with number " +
                        number.getText() +
                        " from group " +
                        group.getText());
            }
        });
    }
}