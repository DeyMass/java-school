package edu.mikhail.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

class StateRecordList
{
    private String state;
    private StateRecordList next;

    public StateRecordList(String _state)
    {
        Log.d("StateChange", "Entered new state: " + _state);
        this.state = _state;
        next = null;
    }

    public void appendState(String _state)
    {
        if (next == null)
            next = new StateRecordList(_state);
        else
            next.appendState(_state);
    }

    public String getState()
    {
        return state;
    }

    public StateRecordList getNext()
    {
        return next;
    }

    public String getList() {
        String baseStr = "The current lifecycle is:\n" + getState();
        StateRecordList p = next;

        while(p != null)
        {
            baseStr += " -> " + p.getState();
            p = p.getNext();
        }

        return baseStr;
    }
};

public class MainActivity extends AppCompatActivity {
    private StateRecordList stateRecordList;
    private TextView textView;

    @Override
    protected void onStart() {
        super.onStart();
        stateRecordList.appendState("onStart");
        textView.setText(stateRecordList.getList());
    }

    @Override
    protected void onStop() {
        super.onStop();
        stateRecordList.appendState("onStop");
        textView.setText(stateRecordList.getList());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stateRecordList.appendState("onDestroy");
        textView.setText(stateRecordList.getList());
    }

    @Override
    protected void onPause() {
        super.onPause();
        stateRecordList.appendState("onPause");
        textView.setText(stateRecordList.getList());
    }

    @Override
    protected void onResume() {
        super.onResume();
        stateRecordList.appendState("onResume");
        textView.setText(stateRecordList.getList());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        stateRecordList.appendState("onRestart");
        textView.setText(stateRecordList.getList());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.plainText);
        stateRecordList = new StateRecordList("onCreate");
        textView.setText(stateRecordList.getList());
    }
}