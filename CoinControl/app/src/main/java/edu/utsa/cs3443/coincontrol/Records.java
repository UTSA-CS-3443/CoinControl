package edu.utsa.cs3443.coincontrol;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.utsa.cs3443.coincontrol.model.FinancialEntry;

/**
 * @author Keanu Anderson-Pola
 * @author Jonathan Berndt
 * The Records class represents the activity for displaying financial records (earnings and expenses)
 * for a selected month in the CoinControl app.
 */
public class Records extends AppCompatActivity implements View.OnClickListener {

    /** The selected month for which the financial records are displayed. */
    private String selectedMonth;

    /** List of financial entries representing earnings for the selected month. */
    private ArrayList<FinancialEntry> earnings;

    /** List of financial entries representing expenses for the selected month. */
    private ArrayList<FinancialEntry> expenses;

    /** TextView for displaying earnings in the UI. */
    private TextView earningsTextView;

    /** TextView for displaying expenses in the UI. */
    private TextView expensesTextView;

    /**
     * Called when the activity is starting. Responsible for initializing the activity, such as
     * setting up the user interface, associating click events for buttons, and retrieving the selected month.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently supplied
     *                           in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        // Initialize ArrayLists
        earnings = new ArrayList<>();
        expenses = new ArrayList<>();

        // Set up UI components
        setupButton(R.id.records_done_button);
        setupTextViews();

        // Load financial entries from the CSV file
        setupArrayLists();

        // Display financial entries in the UI
        printFinances();
    }

    /**
     * Sets up TextViews for displaying earnings, expenses, and the selected month in the UI.
     */
    private void setupTextViews() {
        selectedMonth = getIntent().getStringExtra("SELECTED_MONTH");
        earningsTextView = findViewById(R.id.recordsEarnings_textView);
        expensesTextView = findViewById(R.id.recordsExpenses_textView);
        TextView monthTextView = findViewById(R.id.recordsMonth_textView);
        monthTextView.setText(selectedMonth);
    }

    /**
     * Called when a view has been clicked. Handles button clicks and invokes corresponding methods.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.records_done_button) {
            launchActivity();
        }
    }

    /**
     * Sets up ArrayLists for earnings and expenses by reading data from the CSV file.
     */
    public void setupArrayLists() {
        selectedMonth = getIntent().getStringExtra("SELECTED_MONTH");
        String filePath = getFilesDir() + "/" + selectedMonth.toLowerCase() + ".csv";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                FinancialEntry record = new FinancialEntry(tokens[0], tokens[1], Double.parseDouble(tokens[2]));
                if (record.isEarning()) {
                    earnings.add(record);
                } else if (record.isExpense()) {
                    expenses.add(record);
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays earnings and expenses in the UI.
     */
    private void printFinances() {
        String earningList = "Earnings:\n";
        String expenseList = "Expenses:\n";

        for (int i = 0; i < earnings.size(); ++i) {
            earningList = earningList.concat(earnings.get(i).getName() + ": $" + earnings.get(i).getAmount() + "\n");
        }
        earningsTextView.setText(earningList);

        for (int i = 0; i < expenses.size(); ++i) {
            expenseList = expenseList.concat(expenses.get(i).getName() + ": $" + expenses.get(i).getAmount() + "\n");
        }
        expensesTextView.setText(expenseList);
    }

    /**
     * Launches the Menu activity.
     */
    private void launchActivity() {
        Intent intent = new Intent(this, Menu.class);
        intent.putExtra("SELECTED_MONTH", selectedMonth);
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
