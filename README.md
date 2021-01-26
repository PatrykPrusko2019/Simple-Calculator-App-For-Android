# SimpleCalculatorAppForAndroid

A simple calculator app for android. Created GUI for vertical and horizontal activity. Consists of 20 buttons:
- 16 basic
- 4 additional

Application operation:
- basic calculations addition, subtraction, division, multiplication.

Additional buttons created:
1. Clear button:
clears the fields result, newNumber, operations.

2. Negation button:
* if empty newNumber field adds "-".
* if the minus sign alone in the newNumber field returns an empty field.
* if it has a value other than 0, it changes the sign of the given number.
* if 0 is present, it returns 0 (if -0.0 occurs, it will return 0).

3. Modulo button (MOD):
* when there is a value in the newNumber field, it runs the performOperation (value, "%") method: calculates the final result, changes the current "%" sign, sets the result field, clears the newNumber field.
* when there is no new value, it sets the current sign of the operator "%"

4. Square root button:
* if there is a new value in the newNumber field, do the square root, the operator changes to "=".
* if there is no new value, but there is a result, then root.
* if the result of division by zero, then it is not a root.
* finally, the newNumber field resets.

Additionally, when used with horizontal and vertical activity, it saves 2 states of firstNumber and the operator to be able to continue calculations.
Extra action, if you divide by 0, or divide modulo by 0, you will get the message: "Division by zero is error"!
