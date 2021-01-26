package com.patrykprusko.simplecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * A simple calculator app for android. Created GUI for vertical and horizontal activity. Consists of 20 buttons:
 * - 16 basic
 * - 4 additional
 * Application operation:
 * - basic calculations addition, subtraction, division, multiplication.
 *
 * Additional buttons created:
 * 1. Clear button: clears the fields result, newNumber, operations.
 * 2. Negation button:
 * * if empty newNumber field adds "-".
 * * if the minus sign alone in the newNumber field returns an empty field.
 * * if it has a value other than 0, it changes the sign of the given number.
 * * if 0 is present, it returns 0 (if -0.0 occurs, it will return 0).
 *
 * 3. Modulo button (MOD):
 * * when there is a value in the newNumber field, it runs the performOperation (value, "%") method:
 * calculates the final result, changes the current "%" sign, sets the result field, clears the newNumber field.
 * * when there is no new value, it sets the current sign of the operator "%"
 *
 * 4. Square root button:
 * * if there is a new value in the newNumber field, do the square root, the operator changes to "=".
 * * if there is no new value, but there is a result, then root.
 * * if the result of division by zero, then it is not a root.
 * * finally, the newNumber field resets.
 *
 * Additionally, when used with horizontal and vertical activity, it saves 2 states of firstNumber and the operator to
 * be able to continue calculations.
 * Extra action, if you divide by 0, or divide modulo by 0, you will get the message: "Division by zero is error"!
 */

public class MainActivity extends Activity {

    private EditText newNumber;
    private EditText result;
    private TextView operations;

    private Double firstNumber = null;
    private String currentOperator = "=";

    private static String STATE_ACTUAL_FIRST_NUMBER = "StateFirstNumber"; // current data states of objects
    private static String STATE_CURRENT_OPERATOR = "StateCurrentOperator";
    private static String STATE_CURRENT_RESULT = "StateCurrentResult";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newNumber = (EditText) findViewById(R.id.newNumber);
        result = (EditText) findViewById(R.id.result);
        operations = (TextView) findViewById(R.id.operations);



        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttonDot);

        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonDivine = findViewById(R.id.buttonDivide);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonEquals = findViewById(R.id.buttonEquals);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNumber.append(b.getText().toString());
            }
        };

        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);


        //created operations
        View.OnClickListener listenerOperation = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String valueStr = newNumber.getText().toString();

                try {

                    Double newValue = Double.valueOf(valueStr);
                        performOperation(newValue, op);

                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }

                    currentOperator = op;

                    operations.setText(currentOperator); // inserts the current character

                if( firstNumber != null ) result.setText(firstNumber.toString()); // inserts a new result
                    newNumber.setText("");

            }
        };

        buttonPlus.setOnClickListener(listenerOperation);
        buttonMinus.setOnClickListener(listenerOperation);
        buttonDivine.setOnClickListener(listenerOperation);
        buttonMultiply.setOnClickListener(listenerOperation);
        buttonEquals.setOnClickListener(listenerOperation);

        // create a clear button
        Button buttonClear = (Button) findViewById(R.id.buttonClear);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                result.setText("");
                newNumber.setText(""); // clears the data
                operations.setText("");

                currentOperator = "=";
                if(firstNumber != null) firstNumber = null;
            }
        });

        //create a negation button
        Button buttonNeg = (Button) findViewById(R.id.buttonNegation);

        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String value = newNumber.getText().toString();
                    if (value.length() == 0) {
                        newNumber.setText("-");
                    } else if (value.equals("-")) { // if it is only the character then clear the newNumber field
                        newNumber.setText("");
                    } else {

                        Double valueDouble = Double.valueOf(value);

                        if(valueDouble != 0.0) {
                                valueDouble *= (-1);
                            newNumber.setText(valueDouble.toString());
                        } else {
                            newNumber.setText("0"); // so that it doesn't jump -0.0
                        }
                    }

            }
        });

        //create a modulo button
        Button buttonMod = (Button) findViewById(R.id.buttonMod);

        buttonMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    try {
                            Double value = Double.valueOf(newNumber.getText().toString());
                            performOperation(value, "%");

                        if( ! (result.getText().equals("Division by zero is error")) && firstNumber != null ) result.setText(firstNumber.toString()); // inserts a new result
                                currentOperator = "%";
                                operations.setText(currentOperator);

                            newNumber.setText("");

                    } catch (NumberFormatException e) {
                        newNumber.setText(""); // if there is no new variable, then change the sign of the operator
                        currentOperator = "%";
                        operations.setText(currentOperator);
                    }

            }
        });

        //create a square root button
        Button buttonSquareRoot = (Button) findViewById(R.id.buttonSquareRoot);


        buttonSquareRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    try {
                            Double value = Double.valueOf(newNumber.getText().toString());
                            if(value == -0.0) value = 0.0; // to get rid of -0.0 -> 0.0
                            value = Math.sqrt(value);
                            result.setText(value.toString()); // the result after the square root
                            firstNumber = value;
                            currentOperator = "=";
                            operations.setText(currentOperator);

                    } catch (NumberFormatException e) {
                        newNumber.setText(""); // if there is no new value in the newNumber field
                        String valueResult = result.getText().toString();
                        if(valueResult.length() != 0 && ! (valueResult.equals("Division by zero is error")) ) {
                            Double value = Double.valueOf(valueResult);
                            value = Math.sqrt(value);
                            result.setText(value.toString()); // the result value after the square root
                            firstNumber = value;
                            currentOperator = "=";
                            operations.setText(currentOperator);
                        } else {
                            result.setText("");
                            currentOperator = "=";
                            operations.setText(currentOperator);
                        }
                    }
                    newNumber.setText("");
            }
        });



    }

    //saves 3 states here
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putString(STATE_CURRENT_OPERATOR, currentOperator);

        outState.putString(STATE_CURRENT_RESULT, result.getText().toString()) ; // created for division by 0

        if(firstNumber != null) {
            outState.putDouble (STATE_ACTUAL_FIRST_NUMBER, firstNumber);
        }

        super.onSaveInstanceState(outState);
    }

    //here restores 2 states
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        currentOperator = savedInstanceState.getString(STATE_CURRENT_OPERATOR);

        String currentResult = savedInstanceState.getString(STATE_CURRENT_RESULT);
        if(currentResult.equals("Division by zero is error")) { // if it was division / modulo by 0, set firstNumber to null
            firstNumber = null;
        } else {
            firstNumber = savedInstanceState.getDouble(STATE_ACTUAL_FIRST_NUMBER);
        }

        operations.setText(currentOperator);
    }




    private void performOperation(Double value, String op) {

        if(value == -0.0) value = 0.0; // to get rid of -0.0 -> 0.0

        if( firstNumber == null ) {
            firstNumber = value;

        } else { // is already the result

            if(currentOperator.equals("=")) {
                currentOperator = op;
            } else if(currentOperator.equals("%") && op.equals("=")) {
                currentOperator = "%";
            }

            switch ( currentOperator ) {

                case "=":
                    firstNumber = value;
                    break;

                case "+":
                    firstNumber += value;
                    break;

                case "-":
                    firstNumber -= value;
                    break;

                case "*":
                    firstNumber *= value;
                    break;

                case "/":
                    if(value != 0) {
                        firstNumber /= value;
                    } else {
                        firstNumber = null;
                        result.setText("Division by zero is error");
                    }
                    break;

                case "%":
                    if(value != 0) {
                        firstNumber %= value;
                    } else {
                        firstNumber = null;
                        result.setText("Division by zero is error");
                    }
                    break;
            }

        }
    }

}