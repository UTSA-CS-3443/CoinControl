package edu.utsa.cs3443.coincontrol;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author Carolina Pindter
 * The MainActivity class represents the main activity of the CoinControl app.
 * It serves as the entry point and provides a button to start the app by navigating to the SelectMonth activity.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Called when the activity is starting. Responsible for initializing the activity, such as
     * setting up the user interface and associating the click event for the start button.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently supplied
     *                           in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the start button
        setupButton(R.id.main_start_button);
    }

    /**
     * Called when a view has been clicked. Handles button clicks and invokes corresponding methods.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.main_start_button) {
            launchActivity();
        }
    }

    /**
     * Launches the SelectMonth activity to start the CoinControl app.
     */
    private void launchActivity() {
        Intent intent = new Intent(this, SelectMonth.class);
        startActivity(intent);
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
}
