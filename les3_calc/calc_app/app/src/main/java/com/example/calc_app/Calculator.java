package com.example.calc_app;

import java.util.NoSuchElementException;
import java.util.Stack;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michail
 */

public class Calculator{
    public static final int UHIGH= 5;
    public static final int HIGH = 4;
    public static final int LOW  = 3;
    public static final int ULOW = 2;
    public static final int ERROR=-1;

    Stack<String> st;
    String notated;

    private boolean checkFuncSym(char sym){
        switch(sym){
            case '*':
            case '+':
            case '-':
            case '/':
            case '(':
            case '√':
            case '^':
                return true;
            default:
                return false;
        }
    }

    private boolean is_binary(char oper){
        switch(oper){
            case '+':
            case '-':
            case '/':
            case '*':
            case '^':
                return true;
            case '√':
            default:
                return false;
        }
    }

    private int get_priority(char oper){
        switch (oper){
            case '√':
            case '^':
                return UHIGH;
            case '*':
            case '/':
                return HIGH;
            case '+':
            case '-':
                return LOW;
            case '(':
                return ULOW;
            default:
                return ERROR;
        }
    }

    private void add(char oper){
        if (oper != '(')
            if(!st.isEmpty() && get_priority(oper) <= get_priority(st.lastElement().charAt(0))){
                notated += st.lastElement();
                notated += ' ';
                st.pop();
            }
        st.push(String.valueOf(oper));
    }

    private boolean isNumber(char sym){
        if (sym >= '0' && sym <= '9')
            return true;
        return false;
    }

    public double solve(String str){
        st = new Stack<>();
        notated = "";
        notate(str);
        System.out.println(notated);
        Stack<Double> res = new Stack<>();
        double	num = 0;
        int	znak = 1;
        boolean	newNum = false;
        boolean isDecimal = false;
        double  mod = 0.1;
        //printf(" \n%s\n", notated);
        for (int i = 0; i < notated.length(); i++){
            char sym = notated.charAt(i);

            if (sym == '-' && (i+1) < notated.length() && notated.charAt(i+1) != ' '){
                znak *= -1;
                continue;
            }
            if (sym == '-' && (i-1) < 0){
                znak *= -1;
                continue;
            }
            if (sym == ',') isDecimal = true;
            if (isNumber(sym) && !isDecimal){
                newNum = true;
                num *= 10;
                num += (double)(sym - '0');
                continue;
            }
            if (isNumber(sym) && isDecimal){
                num += ((double)(sym - '0')) * mod;
                mod *= 0.1;
            }
            if (sym == ' '){
                isDecimal = false;
                mod = 0.1;
                if (!newNum)
                    continue;
                num *= znak;
                znak = 1;
                newNum = false;
                res.push(num);
                num = 0;
                continue;
            }
            if (checkFuncSym(notated.charAt(i))){
                if (!is_binary(sym)){
                    double x1;
                    try {
                        x1 = res.lastElement();
                    }
                    catch (NoSuchElementException exc){
                        throw new ArithmeticException();
                    }
                    res.pop();
                    switch(sym){
                        case '√':
                            res.push(Math.sqrt(x1));
                            break;
                        default:
                            throw new ArithmeticException();
                    }
                    continue;
                }
                double x1, x2;
                try {
                    x2 = res.lastElement();
                    res.pop();
                    x1 = res.lastElement();
                    res.pop();
                }
                catch (NoSuchElementException e){
                    throw new ArithmeticException();
                }
                switch(sym){
                    case '+':
                        res.push(x1+x2);
                        break;
                    case '-':
                        res.push(x1-x2);
                        break;
                    case '*':
                        res.push(x1*x2);
                        break;
                    case '/':
                        double answ;
                        try{
                            answ = x1/x2;
                        }
                        catch (ArithmeticException e){
                            throw new ArithmeticException();
                        }
                        res.push(answ);
                        break;
                    case '^':
                        res.push(Math.pow(x1,x2));
                        break;
                    default:
                        throw new ArithmeticException();
                }
            }
        }
        if (res.isEmpty()){
            num *= znak;
            res.push(num);
        }

        return res.lastElement();
    }

    public void notate(String str){
        str.replaceAll("-√", "-1*√");

        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i) == ' ') continue;
            if (isNumber(str.charAt(i)) || str.charAt(i) == ','){
                notated += str.charAt(i);
                continue;
            }
            notated += ' ';
            //printf("\n%s",notated);

            if (checkFuncSym(str.charAt(i))){
                if ((str.charAt(i) == '-' && i+1 < str.length() && isNumber(str.charAt(i+1)) &&
                        (i-1) >= 0 && str.charAt(i - 1) == '(') ||
                        (str.charAt(i) == '-' && i-1 < 0)){
                    notated += '-';
                    continue;
                }
                add(str.charAt(i));
            }
            for (int l = 0; l < st.size(); l++)
                System.out.print(st.elementAt(l) + " ");
            System.out.println();
            if (str.charAt(i) == ')'){
                while(st.lastElement().charAt(0) != '('){
                    try{
                        notated += st.lastElement();
                        st.pop();
                        st.lastElement();
                    }
                    catch (NoSuchElementException e){
                        throw new ArithmeticException();
                    }
                }
                if (!st.isEmpty())
                    st.pop();
            }
        }
        for (int i = 0; i < notated.length(); i++){
            if (!st.isEmpty()){
                notated += ' ';
                notated += st.lastElement();
                notated += ' ';
                st.pop();
            }
        }
    }
}
