package com.example.calc_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView result;
    TextView expr;
    DragAndDropRing ring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        expr = findViewById(R.id.expression);
        ring = new DragAndDropRing(findViewById(R.id.dragable_ring), findViewById(R.id.expression), getApplicationContext());

        View v = findViewById(R.id.activity_main);
        v.setOnTouchListener(ring);
    }

    private boolean checkFuncSym(char sym){
        switch(sym){
            case '*':
            case '+':
            case '-':
            case '/':
            case ',':
                return true;
            default:
                return false;
        }
    }

    public void getAnswer(View v){
        Calculator calc = new Calculator();
        double d;
        try {
            d = calc.solve(expr.getText().toString());
        }
        catch(ArithmeticException e) {
            result.setText("ERROR");
            return;
        }
        result.setText(String.valueOf(d));

    }

    public void buttonRemove(View v){
        if (expr.getText().length() > 0) {
            expr.setText(expr.getText().subSequence(0, expr.length() - 1));
        }
    }

    public void buttonClick(View v) {
        Button but = findViewById(v.getId());
        int len = expr.length();

        CharSequence tickTxt = expr.getText();
        if (tickTxt.length() > 0 && checkFuncSym(but.getText().toString().charAt(0))){
            char lastChar = tickTxt.charAt(len - 1);
            switch (lastChar) {
                case '+':
                case '-':
                case '*':
                case '/':
                case ',':
                case '^':
                    expr.setText(tickTxt.subSequence(0, expr.length() - 1));
                default:
                    break;
            }
        }
        System.out.print(but.getText());
        expr.append(but.getText());
    }

    public void buttonClear(View v){
        System.out.print("Clear textView");
        expr.setText("");
    }


}