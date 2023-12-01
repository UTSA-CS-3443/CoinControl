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
 * The EditEarning class is an activity that allows the user to edit an existing earning entry for a specific month.
 * It provides functionality to update the earning entry in the CSV file and navigate back to the Edit activity.
 */
public class EditEarning extends AppCompatActivity implements View.OnClickListener {

    /** EditText for entering the name of the earning to be edited. */
    private EditText earningNameEditText;

    /** EditText for entering the new amount of the earning. */
    private EditText earningAmountEditText;

    /** Button to confirm the edit operation. */
    private Button doneButton;

    /** The selected month for which the user is editing an earning entry. */
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
        setContentView(R.layout.activity_edit_earning);

        // Display a toast message with instructions for the user
        Toast.makeText(this, "Please enter the name of the earning to change and the amount to change it to (0 to remove) (NO COMMAS)", Toast.LENGTH_SHORT).show();

        // Initialize UI elements
        earningNameEditText = findViewById(R.id.editEarningName_editText);
        earningAmountEditText = findViewById(R.id.editEarningAmount_editText);
        doneButton = findViewById(R.id.editEarningDone_button);
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
        if (view.getId() == R.id.editEarningDone_button) {
            editEarning();
            launchActivity();
        }
    }

    /**
     * Edits the existing earning entry based on the user input and updates the CSV file.
     * Displays appropriate toast messages for success or failure.
     */
    private void editEarning() {
        // Retrieve the selected month from the intent
        selectedMonth = getIntent().getStringExtra("SELECTED_MONTH");
        String earningName = earningNameEditText.getText().toString().trim();
        String earningAmountStr = earningAmountEditText.getText().toString().trim();

        // Validate user input
        if (earningName.isEmpty() || earningAmountStr.isEmpty()) {
            Toast.makeText(this, "Please enter both earning name and amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double earningAmount = Double.parseDouble(earningAmountStr);

        if (selectedMonth != null) {
            String filePath = getFilesDir() + "/" + selectedMonth.toLowerCase() + ".csv";

            try {
                if (isEarningExist(filePath, earningName)) {
                    updateEarning(filePath, earningName, earningAmount);
                    Toast.makeText(this, "Earning updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Earning not found", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error editing earning", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error: Selected month is null", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checks if an earning entry with the specified name already exists in the given CSV file.
     *
     * @param filePath    The path to the CSV file.
     * @param earningName The name of the earning entry to check.
     * @return True if the earning entry exists, false otherwise.
     * @throws IOException If an I/O error occurs while reading the CSV file.
     */
    private boolean isEarningExist(String filePath, String earningName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(",");
            String existingEarningName = parts[0].trim();

            if (existingEarningName.equals(earningName)) {
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
     * Updates the existing earning entry in the CSV file with the new amount.
     *
     * @param filePath      The path to the CSV file.
     * @param earningName    The name of the earning entry to update.
     * @param earningAmount  The new amount of the earning entry.
     * @throws IOException If an I/O error occurs while reading or writing the CSV file.
     */
    private void updateEarning(String filePath, String earningName, double earningAmount) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        StringBuilder fileContents = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(",");
            String existingEarningName = parts[0].trim();
            double existingEarningAmount = Double.parseDouble(parts[2].trim());

            if (existingEarningName.equals(earningName)) {
                existingEarningAmount = earningAmount;
            }

            if (parts[1].equals("+")) {
                fileContents.append(existingEarningName).append(",+,").append(existingEarningAmount).append("\n");
            } else if (parts[1].equals("-")) {
                fileContents.append(existingEarningName).append(",-,").append(existingEarningAmount).append("\n");
            }
        }

        bufferedReader.close();

        FileWriter fileWriter = new FileWriter(filePath, false);
        fileWriter.write(fileContents.toString());
        fileWriter.close();
    }
}
