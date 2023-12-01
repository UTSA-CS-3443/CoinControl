package edu.utsa.cs3443.coincontrol;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

/**
 * @author Makala Roberson
 * @author Jonathan Berndt
 * The CreateEarning class is an activity that allows the user to create a new earning entry for a specific month.
 * It provides functionality to save the earning entry to a CSV file and navigate back to the Create activity.
 */
public class CreateEarning extends AppCompatActivity implements View.OnClickListener {

    /** The selected month for which the user is creating an earning entry. */
    private String selectedMonth;

    /**
     * Called when the activity is starting. Responsible for initializing the activity, such as
     * setting up the user interface and displaying a toast message for user guidance.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently supplied
     *                           in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_earning);

        // Set up click listener for the "Done" button
        setupButton(R.id.done_button);

        // Display a toast message with instructions for the user
        Toast.makeText(this, "Please enter both earning name and amount (NO COMMAS)", Toast.LENGTH_SHORT).show();
    }

    /**
     * Called when a button is clicked. Handles button clicks and invokes corresponding methods.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.done_button) {
            saveEarning();
            launchActivity();
        }
    }

    /**
     * Saves the entered earning information to a CSV file for the selected month.
     * Displays appropriate toast messages for success or failure.
     */
    private void saveEarning() {
        // Retrieve references to the UI elements
        EditText earningNameEditText = findViewById(R.id.createEarningName_editText);
        EditText earningAmountEditText = findViewById(R.id.createEarningAmount_editText);
        selectedMonth = getIntent().getStringExtra("SELECTED_MONTH");

        // Retrieve user input from the UI elements
        String earningName = earningNameEditText.getText().toString().trim();
        String earningAmountStr = earningAmountEditText.getText().toString().trim();

        // Validate user input
        if (earningName.isEmpty() || earningAmountStr.isEmpty()) {
            Toast.makeText(this, "Please enter both earning name and amount (NO COMMAS)", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Parse the earning amount and create a CSV line
            double earningAmount = Double.parseDouble(earningAmountStr);
            char sign = '+';
            String csvLine = earningName + "," + sign + "," + earningAmount + "\n";

            // Define the file path for the CSV file
            String filePath = getFilesDir() + "/" + selectedMonth.toLowerCase() + ".csv";

            // Check if the entry already exists in the CSV file
            if (!entryExists(filePath, earningName)) {
                // Append the CSV line to the file and display a success message
                FileWriter fileWriter = new FileWriter(filePath, true);
                fileWriter.append(csvLine);
                fileWriter.close();
                Toast.makeText(this, "Earning saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Display a message if the earning name already exists
                Toast.makeText(this, "Earning name already exists", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            // Display a message for an invalid amount format
            Toast.makeText(this, "Invalid amount format", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // Display a message for an error while saving the earning
            Toast.makeText(this, "Error saving earning", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * Checks if an entry with the specified name already exists in the given CSV file.
     *
     * @param filePath   The path to the CSV file.
     * @param entryName  The name of the entry to check.
     * @return True if the entry exists, false otherwise.
     */
    private boolean entryExists(String filePath, String entryName) {
        try {
            // Check if the entry name already exists in the CSV file
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(entryName)) {
                    br.close();
                    return true;
                }
            }
            br.close();
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Launches the Create activity to allow the user to continue creating entries.
     */
    private void launchActivity() {
        Intent intent = new Intent(this, Create.class);
        intent.putExtra("SELECTED_MONTH", selectedMonth);
        startActivity(intent);
    }

    /**
     * Sets up a click listener for the specified button ID.
     *
     * @param buttonID The resource ID of the button to set up with a click listener.
     */
    private void setupButton(int buttonID) {
        Button button = findViewById(buttonID);
        button.setOnClickListener(this);
    }
}
