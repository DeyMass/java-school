package edu.mikhail.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // I wish there would be just a class 'User'...
        // But what do we say to death? Not today!
        String ids[] = {
                "1",
                "2",
                "3",
                "4",
                "5",
        };
        String data[] = {
                "Name", "Number", "Group",
                "Ivan", "88005553535", "Common",
                "Piotr", "12345", "Developer",
                "Pavel", "999000", "Manager",
                "Kirk", "001100", "Spok"
        };

        ListView lv;

        lv = findViewById(R.id.listView);

        lv.setAdapter(new CustomListAdapter(this, ids, data));
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