package edu.utsa.cs3443.coincontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

/**
 * @author Keanu Anderson-Pola
 * The SelectMonth class represents the activity for selecting a month in the CoinControl app.
 * Users can choose a specific month from a spinner and proceed to view financial information for that month.
 */
public class SelectMonth extends AppCompatActivity implements View.OnClickListener {

    /** Spinner for selecting a month. */
    private Spinner monthSpinner;

    /**
     * Called when the activity is starting. Responsible for initializing the activity, such as
     * setting up the user interface and associating click events for buttons.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently supplied
     *                           in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_month);
        monthSpinner = findViewById(R.id.monthSpinner);
        setupButton(R.id.month_done_button);
    }

    /**
     * Handles click events for the "Done" button. Invoked when the button is clicked,
     * triggering the launch of the Menu activity for the selected month.
     *
     * @param view The view that was clicked.
     */
    public void onClick(View view) {
        if (view.getId() == R.id.month_done_button) {
            launchActivity();
        }
    }

    /**
     * Launches the Menu activity for the selected month.
     */
    private void launchActivity() {
        String selectedMonth = monthSpinner.getSelectedItem().toString();
        Intent intent = new Intent(this, Menu.class);
        intent.putExtra("SELECTED_MONTH", selectedMonth);
        startActivity(intent);
    }

    /**
     * Sets up a button with the specified ID and associates it with the click event.
     *
     * @param buttonID The resource ID of the button to set up.
     */
    private void setupButton(int buttonID){
        Button button = findViewById(buttonID);
        button.setOnClickListener(this);
    }
}
