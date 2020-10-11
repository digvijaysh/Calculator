package com.example.calculator;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.enter);
        textView = (TextView) findViewById(R.id.answer);
        button = (Button) findViewById(R.id.del);


        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editText.setText("");
                return true;
            }
        });
    }

    public void zero(View view) {
        editText.append("0");
    }

    public void one(View view) {
        editText.append("1");
    }

    public void two(View view) {
        editText.append("2");
    }

    public void three(View view) {
        editText.append("3");
    }

    public void four(View view) {
        editText.append("4");
    }

    public void five(View view) {
        editText.append("5");
    }

    public void six(View view) {
        editText.append("6");
    }

    public void seven(View view) {
        editText.append("7");
    }

    public void eight(View view) {
        editText.append("8");
    }

    public void nine(View view) {
        editText.append("9");
    }

    public void openBrackets(View view) {
        editText.append("(");
    }

    public void closedBrackets(View view) {
        editText.append(")");
    }

    public void add(View view) {
        editText.append("+");
    }

    public void sub(View view) {
        editText.append("-");
    }

    public void multiply(View view) {
        editText.append("\u00D7");
    }

    public void divide(View view) {
        editText.append("\u00F7");
    }

    public void dot(View view) {
        editText.append(".");
    }

    public void equal(View view) {
        String s = editText.getText().toString();
        if(isValid(s) == false)
        {
            textView.setText("Invalid Input");
            return;
        }
        String x = calculate(s);
        if (x.equals("Infinity"))
            textView.setText("Cannot divide by 0");
        else
            textView.setText(x);
    }

    public void delete(View view) {
        String s = editText.getText().toString();
        if (!s.equals(""))
            editText.setText(s.substring(0, s.length() - 1));
        if(s.equals(""))
            textView.setText("");
    }

    public String calculate(String expression) {
        Queue<Character> queue = new ArrayDeque<Character>();
        char[] c = expression.toCharArray();
        int len = c.length;
        for (int i = 0; i < len; i++) {
            char exp = expression.charAt(i);
            if (exp == ' ') continue;
            queue.offer(exp);
        }
        queue.offer('+');
        try {
            return evaluate(queue) + "";
        } catch (ArithmeticException e) {
            return "zero";
        }
    }

    public boolean isValid(String expression) {
        Stack<Character> stack = new Stack<>();
        for(char s:expression.toCharArray())
        {
            if(s=='(')
                stack.push(')');
            if(s == ')')
            {
                if(stack.isEmpty() || stack.pop()!=s)
                    return false;
            }
        }
        return stack.isEmpty();
    }

    public double evaluate(Queue<Character> queue) {
        char pre = '+';
        double num = 0, total = 0, previous = 0;

        while (!queue.isEmpty()) {
            char c = queue.poll();

            if (c == '.') {
                double multiply = 0.1;
                double num1 = 0;
                while (!queue.isEmpty() && Character.isDigit(queue.peek())) {
                    char c2 = queue.poll();
                    num1 = num1 + (c2 - '0') * multiply;
                    multiply = multiply / 10;
                }
                num = num + num1;
            }

            if ('0' <= c && c <= '9') {
                num = num * 10 + c - '0';
            } else if (c == '(') {
                num = evaluate(queue);
            } else {
                switch (pre) {
                    case '+':
                        total += previous;
                        previous = num;
                        break;
                    case '-':
                        total += previous;
                        previous = -num;
                        break;
                    case '\u00D7':
                        previous *= num;
                        break;
                    case '\u00F7':
                        previous /= num;
                        break;
                }
                if (c == ')')
                    break;
                pre = c;
                num = 0;
            }
        }
        return total + previous;
    }

}