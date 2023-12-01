package edu.utsa.cs3443.coincontrol;

// Import the required libraries
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.utsa.cs3443.coincontrol.model.FinancialEntry;

/**
 * @author Jonathan Berndt
 * The Results class represents the activity for displaying spending results
 * (visualized in a pie chart) for a selected month in the CoinControl app.
 */
public class Results extends AppCompatActivity implements View.OnClickListener {

    /** The selected month for which the spending results are displayed. */
    private String selectedMonth;

    /** List of financial entries representing earnings for the selected month. */
    private ArrayList<FinancialEntry> earnings;

    /** List of financial entries representing expenses for the selected month. */
    private ArrayList<FinancialEntry> expenses;

    /** PieChart for visualizing expenses in the UI. */
    private PieChart pieChart;

    /** List of colors for styling the pie chart. */
    private ArrayList<String> colors;

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
        setContentView(R.layout.activity_results);

        // Initialize ArrayLists and colors
        colors = new ArrayList<>();
        colors.add("Yellow");
        colors.add("Green");
        colors.add("Red");
        colors.add("Blue");
        colors.add("Orange");
        colors.add("Purple");

        // Initialize ArrayLists
        earnings = new ArrayList<>();
        expenses = new ArrayList<>();

        // Set up UI components
        setupTextViews();
        setupArrayLists();
        pieChart = findViewById(R.id.piechart);

        // Create pie chart data and set it
        setData();

        // Display expense details and set up button click event
        setExpenseText();
        setupButton(R.id.results_done_button);
    }

    /**
     * Sets up TextViews for displaying the selected month in the UI.
     */
    private void setupTextViews() {
        selectedMonth = getIntent().getStringExtra("SELECTED_MONTH");
        TextView monthTextView = findViewById(R.id.resultsMonth_textView);
        monthTextView.setText("Spending Results for " + selectedMonth);
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
     * Displays expense details in the UI.
     */
    public void setExpenseText() {
        TextView legendText = findViewById(R.id.resultsLegend_textView);
        TextView expenseText = findViewById(R.id.resultsExpenses_textView);
        String expenseList = "\n";
        String legendList = "\n";
        double expenseTotal = 0;
        double earningTotal = 0;
        for (int i = 0; i < expenses.size(); ++i) {
            expenseTotal = expenseTotal + expenses.get(i).getAmount();
        }
        for (int i = 0; i < expenses.size(); ++i) {
            legendList = legendList.concat(expenses.get(i).getName() + ": (" + colors.get(i % 6) + ")\n");
            expenseList = expenseList.concat(expenses.get(i).getName() + "          " +
                    String.format("%.2f", ((expenses.get(i).getAmount() / expenseTotal) * 100)) + "%\n");
        }
        for (int i = 0; i < earnings.size(); ++i) {
            earningTotal = earningTotal + earnings.get(i).getAmount();
        }
        expenseList = expenseList.concat("\n You spent $" + expenseTotal + " out of $" + earningTotal +
                " total. (This is " + String.format("%.2f", ((expenseTotal / earningTotal) * 100)) + "% of total earnings.");
        expenseText.setText(expenseList);
        legendText.setText(legendList);
    }

    /**
     * Creates data for the pie chart and sets it.
     */
    private void setData() {
        ArrayList<String> color = new ArrayList<>();

        color.add("#F4D03F");
        color.add("#1E8449");
        color.add("#A93226");
        color.add("#2471A3");
        color.add("#CA6F1E");
        color.add("#6C3483 ");

        // Set the data and color to the pie chart
        for (int i = 0; i < expenses.size(); ++i) {
            pieChart.addPieSlice(
                    new PieModel(expenses.get(i).getName(), (float) expenses.get(i).getAmount(),
                            Color.parseColor(color.get(i % 6))));
        }

        // To animate the pie chart
        pieChart.startAnimation();
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
        if (view.getId() == R.id.results_done_button) {
            launchActivity();
        }
    }

    /**
     * Launches the Menu activity.
     */
    private void launchActivity() {
        Intent intent = new Intent(this, Menu.class);
        intent.putExtra("SELECTED_MONTH", selectedMonth);
        startActivity(intent);
    }
}
