package edu.utsa.cs3443.coincontrol.model;

/**
 * @author Keanu Anderson-Pola
 * @author Jonathan Berndt
 * The FinancialEntry class represents a financial record, which can be an expense or an earning,
 * in the CoinControl app.
 */
public class FinancialEntry {

    /** Name of the expense/earning. */
    private String name;

    /** Sign indicating whether it's an expense ("-") or an earning ("+"). */
    private String sign;

    /** Amount of the expense/earning. Positive for earnings, negative for expenses. */
    private double amount;

    /**
     * Constructs a new FinancialEntry with the specified name, sign, and amount.
     *
     * @param name   The name of the expense/earning.
     * @param sign   The sign indicating whether it's an expense ("-") or an earning ("+").
     * @param amount The amount of the expense/earning.
     */
    public FinancialEntry(String name, String sign, double amount) {
        this.name = name;
        this.sign = sign;
        this.amount = amount;
    }

    /**
     * Gets the name of the expense/earning.
     *
     * @return The name of the expense/earning.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the expense/earning.
     *
     * @param name The new name of the expense/earning.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the sign indicating whether it's an expense ("-") or an earning ("+").
     *
     * @return The sign of the expense/earning.
     */
    public String getSign() {
        return sign;
    }

    /**
     * Sets the sign indicating whether it's an expense ("-") or an earning ("+").
     *
     * @param sign The new sign of the expense/earning.
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * Gets the amount of the expense/earning.
     *
     * @return The amount of the expense/earning.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the expense/earning.
     *
     * @param amount The new amount of the expense/earning.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Checks if the financial entry is an expense.
     *
     * @return True if it's an expense, false otherwise.
     */
    public boolean isExpense() {
        return sign.equals("-");
    }

    /**
     * Checks if the financial entry is an earning.
     *
     * @return True if it's an earning, false otherwise.
     */
    public boolean isEarning() {
        return sign.equals("+");
    }
}
