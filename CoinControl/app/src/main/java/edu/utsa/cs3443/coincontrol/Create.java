package edu.utsa.cs3443.coincontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author Jonathan Berndt
 * The Create class is an activity that allows the user to create expenses, earnings, or view results.
 * It implements View.OnClickListener to handle button clicks.
 */
public class Create extends AppCompatActivity implements View.OnClickListener {

    /** The selected month for which the user is creating expenses or earnings. */
    private String selectedMonth;

    /**
     * Called when the activity is starting. Responsible for initializing the activity, such as
     * setting up the user interface and retrieving the selected month from the intent.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently supplied
     *                           in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // Array of button IDs to set up click listeners for
        int[] buttonIds = {R.id.addExpense_button, R.id.addEarning_button, R.id.create_done_button, R.id.create_return_button};

        // Set up click listeners for each button
        for (int i : buttonIds) {
            setupButton(i);
        }

        // Retrieve selected month from the intent
        selectedMonth = getIntent().getStringExtra("SELECTED_MONTH");
    }

    /**
     * Sets up a click listener for the specified button ID.
     *
     * @param buttonId The resource ID of the button to set up with a click listener.
     */
    private void setupButton(int buttonId) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(this);
    }

    /**
     * Called when a button is clicked. Handles button clicks and launches corresponding activities.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addExpense_button) {
            launchActivity("expense");
        }
        if (view.getId() == R.id.addEarning_button) {
            launchActivity("earning");
        }
        if (view.getId() == R.id.create_done_button) {
            launchActivity("done");
        }
        if (view.getId() == R.id.create_return_button){
            launchActivity("return");
        }
    }

    /**
     * Launches the appropriate activity based on the user's choice.
     *
     * @param choice The user's choice, which can be "expense", "earning", or "done".
     */
    private void launchActivity(String choice) {
        Intent intent = null;
        switch (choice) {
            case "expense":
                intent = new Intent(this, CreateExpense.class);
                break;
            case "earning":
                intent = new Intent(this, CreateEarning.class);
                break;
            case "done":
                intent = new Intent(this, Results.class);
                break;
            case "return":
                intent = new Intent(this, Menu.class);
                break;
        }
        if (intent != null) {
            intent.putExtra("SELECTED_MONTH", selectedMonth);
            startActivity(intent);
        }
    }
}
