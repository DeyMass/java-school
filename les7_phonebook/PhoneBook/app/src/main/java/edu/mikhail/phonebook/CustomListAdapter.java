package edu.mikhail.phonebook;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CustomListAdapter extends ArrayAdapter<User> {
    private final Context context;
    private final User[] values;

    public CustomListAdapter(Context _context, User[] users)
    {
        super(_context, R.layout.row_layout, users);
        context = _context;
        values = users;
    }

    @Override
    public View getView(int pos, View contentView, ViewGroup parent)
    {
        TextView rowData_name;
        TextView rowData_number;
        TextView rowData_group;
        ImageView img;
        View v;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.item_layout, parent, false);

        rowData_number = v.findViewById(R.id.row_phone);
        rowData_name = v.findViewById(R.id.row_name);
        rowData_group = v.findViewById(R.id.row_group);
        img = v.findViewById(R.id.contact_image);

        rowData_name.setText(values[pos].getUserName() + " " + values[pos].getForname());
        rowData_number.setText(values[pos].getNumber());

        if (values[pos].getClass() == Developer.class)
        {
            img.setImageResource(R.drawable.developer);
            rowData_group.setText("developer");
        }
        else if (values[pos].getClass() == Manager.class)
        {
            img.setImageResource(R.drawable.manager);
            rowData_group.setText("manager");
        }
        else
        {
            img.setImageResource(R.drawable.unknown);
            rowData_group.setText("UNKNOWN");
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Item clicked: " +
                        rowData_name.getText() +
                        " with number " +
                        rowData_number.getText() +
                        " from group " +
                        rowData_group.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
