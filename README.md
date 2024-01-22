# CoinControl
Project for Team 11

# Synopsis
CoinControl is a comprehensive financial management app designed to track and organize personal expenses and earnings. It provides a user-friendly interface for adding, editing, and viewing financial records for different months. The core component of the app is the FinancialEntry class, which represents a financial record that can be either an expense or an earning​

# Code Example
// Creating a new financial entry
FinancialEntry entry = new FinancialEntry("Groceries", "-", 50.0);

// Checking if the entry is an expense
boolean isExpense = entry.isExpense(); // True

// Retrieving the amount of the entry
double amount = entry.getAmount();

# Instructions
Select the month that applies to your current situation. After you select a month, if nothing was ever created beforehand, go to Create (or else the program will break, so please follow instructions. When creating earning/expenses, select the correct option and type in a name and an amonunt (without commas) and then select done. When done with creating, select done to see results or return to go back to the menu. For editing, select the correct option and then enter a name that already exists in that month's file and the amount to change (if you don't want the earning/expense to mean anything anymore change the amount to 0). When done editing, select done to see results or return to go back to the menu. When results are shown, they will be displayed in a pie chart with 6 different colors (they go in a cycle), and are also displayed on a table with percentages and finally a note of how much you spent compared to earned. When records are shown, the earnings and expenses will be in separate lists on a single screen. Hitting return on the menu page lets you choose a different month.

# Motivation
The primary motivation behind CoinControl is to offer a simple and easy way for individuals to manage their personal finances. The app aims to provide clear insights into user's financial habits and help them make informed decisions.

# Installation 
To install CoinControl, clone the repository and build the project using Android Studio. Ensure you have the latest version of Android SDK installed.

# API Reference
Currently, the API documentation is included in the source code as Javadoc comments. Each class and method has detailed descriptions of its functionality.

# Tests
N/A

# Contributors
Jonathan Berndt, Keanu Anderson-Pola, Makala Roberson, Carolina Pindter

# License
N/A
