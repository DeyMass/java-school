package edu.mikhail.phonebook;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CustomListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public CustomListAdapter(Context _context, String[] ids, String[] data)
    {
        super(_context, R.layout.row_layout, ids);
        context = _context;
        values = data;
    }

    @Override
    public View getView(int pos, View contentView, ViewGroup parent)
    {
        TextView rowData_name;
        TextView rowData_number;
        TextView rowData_group;
        View v;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.item_layout, parent, false);
        rowData_number = v.findViewById(R.id.row_phone);
        rowData_name = v.findViewById(R.id.row_name);
        rowData_group = v.findViewById(R.id.row_group);

        pos *= 3;

            if (pos >= values.length || pos+1 >= values.length || pos+2 >= values.length)
            {
                Log.d("Error", "i >= value_length");
                return v;
            }
            rowData_name.setText(values[pos]);
            rowData_number.setText(values[pos+1]);
            rowData_group.setText(values[pos+2]);

        return v;
    }
}
