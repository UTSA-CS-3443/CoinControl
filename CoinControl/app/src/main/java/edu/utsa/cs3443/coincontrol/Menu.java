package edu.utsa.cs3443.coincontrol;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author Carolina Pindter
 * @author Keanu Anderson-Pola
 * The Menu class represents the main menu of the CoinControl app.
 * It provides options for creating, editing, viewing results, and accessing records for a selected month.
 */
public class Menu extends AppCompatActivity implements View.OnClickListener {

    /** The selected month for which the user is navigating the menu. */
    private String selectedMonth;

    /**
     * Called when the activity is starting. Responsible for initializing the activity, such as
     * setting up the user interface, associating click events for menu buttons, and retrieving the selected month.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently supplied
     *                           in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Set up menu buttons
        int[] buttonIds = {R.id.menu_create_button, R.id.menu_edit_button, R.id.menu_results_button, R.id.menu_records_button, R.id.menu_return_button};
        for (int i : buttonIds) {
            setupButton(i);
        }

        // Retrieve the selected month from the intent
        selectedMonth = getIntent().getStringExtra("SELECTED_MONTH");
    }

    /**
     * Sets up a button with the specified ID and associates it with the click event.
     *
     * @param buttonID The resource ID of the button to set up.
     */
    private void setupButton(int buttonID) {
        Button button = findViewById(buttonID);
        button.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked. Handles button clicks and invokes corresponding methods.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.menu_create_button) {
            launchActivity("create");
        } else if (view.getId() == R.id.menu_edit_button) {
            launchActivity("edit");
        } else if (view.getId() == R.id.menu_results_button) {
            launchActivity("results");
        } else if (view.getId() == R.id.menu_records_button) {
            launchActivity("records");
        }
        else if (view.getId() == R.id.menu_return_button){
            launchActivity("return");
        }
    }

    /**
     * Launches the corresponding activity based on the user's menu choice.
     *
     * @param choice The menu choice indicating the activity to launch.
     */
    private void launchActivity(String choice) {
        Intent intent = null;
        switch (choice) {
            case "create":
                intent = new Intent(this, Create.class);
                break;
            case "edit":
                intent = new Intent(this, Edit.class);
                break;
            case "results":
                intent = new Intent(this, Results.class);
                break;
            case "records":
                intent = new Intent(this, Records.class);
                break;
            case "return":
                intent = new Intent(this, SelectMonth.class);
                break;
        }
        if (intent != null) {
            intent.putExtra("SELECTED_MONTH", selectedMonth);
            startActivity(intent);
        }
    }
}
