package edu.utsa.cs3443.coincontrol;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Makala Roberson
 * @author Jonathan Berndt
 * @author Carolina Pindter
 * The EditExpense class is an activity that allows the user to edit an existing expense entry for a specific month.
 * It provides functionality to update the expense entry in the CSV file and navigate back to the Edit activity.
 */
public class EditExpense extends AppCompatActivity implements View.OnClickListener {

    /** EditText for entering the name of the expense to be edited. */
    private EditText expenseNameEditText;

    /** EditText for entering the new amount of the expense. */
    private EditText expenseAmountEditText;

    /** Button to confirm the edit operation. */
    private Button doneButton;

    /** The selected month for which the user is editing an expense entry. */
    private String selectedMonth;

    /**
     * Called when the activity is starting. Responsible for initializing the activity, such as
     * setting up the user interface, displaying a toast message for user guidance, and retrieving
     * the selected month from the intent.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently supplied
     *                           in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        // Display a toast message with instructions for the user
        Toast.makeText(this, "Please enter the name of the expense to change and the amount to change it to (0 to remove) (NO COMMAS)", Toast.LENGTH_SHORT).show();

        // Initialize UI elements
        expenseNameEditText = findViewById(R.id.editExpenseName_editText);
        expenseAmountEditText = findViewById(R.id.editExpenseAmount_editText);
        doneButton = findViewById(R.id.editExpenseDone_button);
        doneButton.setOnClickListener(this);

        // Retrieve the selected month from the intent
        Intent intent = getIntent();
        selectedMonth = intent.getStringExtra("selectedMonth");
    }

    /**
     * Called when the "Done" button is clicked. Handles button click and invokes corresponding methods.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.editExpenseDone_button) {
            editExpense();
            launchActivity();
        }
    }

    /**
     * Edits the existing expense entry based on the user input and updates the CSV file.
     * Displays appropriate toast messages for success or failure.
     */
    private void editExpense() {
        // Retrieve the selected month from the intent
        selectedMonth = getIntent().getStringExtra("SELECTED_MONTH");
        String expenseName = expenseNameEditText.getText().toString().trim();
        String expenseAmountStr = expenseAmountEditText.getText().toString().trim();

        // Validate user input
        if (expenseName.isEmpty() || expenseAmountStr.isEmpty()) {
            Toast.makeText(this, "Please enter both expense name and amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double expenseAmount = Double.parseDouble(expenseAmountStr);

        if (selectedMonth != null) {
            try {
                String filePath = getFilesDir() + "/" + selectedMonth.toLowerCase() + ".csv";

                if (isExpenseExist(filePath, expenseName)) {
                    updateExpense(filePath, expenseName, expenseAmount);
                    Toast.makeText(this, "Expense updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Expense not found", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error editing expense", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error: Selected month is null", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checks if an expense entry with the specified name already exists in the given CSV file.
     *
     * @param filePath     The path to the CSV file.
     * @param expenseName  The name of the expense entry to check.
     * @return True if the expense entry exists, false otherwise.
     * @throws IOException If an I/O error occurs while reading the CSV file.
     */
    private boolean isExpenseExist(String filePath, String expenseName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(",");
            String existingExpenseName = parts[0].trim();

            if (existingExpenseName.equals(expenseName)) {
                bufferedReader.close();
                return true;
            }
        }

        bufferedReader.close();
        return false;
    }

    /**
     * Launches the Edit activity to allow the user to continue editing entries.
     */
    private void launchActivity() {
        Intent intent = new Intent(this, Edit.class);
        intent.putExtra("SELECTED_MONTH", selectedMonth);
        startActivity(intent);
    }

    /**
     * Updates the existing expense entry in the CSV file with the new amount.
     *
     * @param filePath       The path to the CSV file.
     * @param expenseName     The name of the expense entry to update.
     * @param expenseAmount   The new amount of the expense entry.
     * @throws IOException If an I/O error occurs while reading or writing the CSV file.
     */
    private void updateExpense(String filePath, String expenseName, double expenseAmount) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        StringBuilder fileContents = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(",");
            String existingExpenseName = parts[0].trim();
            double existingExpenseAmount = Double.parseDouble(parts[2].trim());

            if (existingExpenseName.equals(expenseName)) {
                existingExpenseAmount = expenseAmount;
            }

            if (parts[1].equals("+")) {
                fileContents.append(existingExpenseName).append(",+,").append(existingExpenseAmount).append("\n");
            } else if (parts[1].equals("-")) {
                fileContents.append(existingExpenseName).append(",-,").append(existingExpenseAmount).append("\n");
            }
        }

        bufferedReader.close();

        FileWriter fileWriter = new FileWriter(filePath, false);
        fileWriter.write(fileContents.toString());
        fileWriter.close();
    }
}
