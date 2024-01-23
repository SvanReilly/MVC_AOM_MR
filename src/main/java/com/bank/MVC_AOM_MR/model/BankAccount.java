package com.bank.MVC_AOM_MR.model;

import java.util.ArrayList;
import java.util.Date;
import org.bson.Document;

public class BankAccount {
	private String accountNumber;
	private ArrayList<String> owners;
	private double balance;
	private Date startingDate;
	private boolean deleted;

	// Constructor por defecto
	public BankAccount() {
		this.accountNumber="";
	}

	@SuppressWarnings("unchecked")
	public BankAccount(Document bankAccountDoc) {
		this.accountNumber = bankAccountDoc.getString("account_number");
		this.owners = (ArrayList<String>) bankAccountDoc.get("owners");
		this.balance = (double) bankAccountDoc.get("balance");
		this.startingDate = (Date) bankAccountDoc.getDate("starting_date");
		this.deleted = bankAccountDoc.getBoolean("deleted", false);
	}

	// Constructor con parámetros
	public BankAccount(String accountNumber, ArrayList<String> owners, double balance, Date startingDate,
			boolean deleted) {
		this.accountNumber = accountNumber;
		this.owners = owners;
		this.balance = balance;
		this.startingDate = startingDate;
		this.deleted = deleted;
	}

	// Getters y setters para cada atributo

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public ArrayList<String> getOwners() {
		return owners;
	}

	public void setOwners(ArrayList<String> owners) {
		this.owners = owners;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Date getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void depositMoney(double moneyIns) {
		setBalance(getBalance() + moneyIns);
	}

	public void withdrawMoney(double moneyIns) {
		setBalance(getBalance() - moneyIns);
	}

	@Override
	public String toString() {
		return "BankAccount [accountNumber=" + accountNumber + ", owners=" + owners + ", balance=" + balance
				+ ", startingDate=" + startingDate + ", deleted=" + deleted + "]";
	}
}