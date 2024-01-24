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
//	private final String SpanishIBANprefix = "ES94";

	// Constructor por defecto
	public BankAccount() {
		this.accountNumber = "No existe";
	}

	@SuppressWarnings("unchecked")
	public BankAccount(Document bankAccountDoc) {
		this.accountNumber = bankAccountDoc.getString("account_number");
		this.owners = (ArrayList<String>) bankAccountDoc.get("owners");
		this.balance = (double) bankAccountDoc.get("balance");
		this.startingDate = (Date) bankAccountDoc.getDate("starting_date");
		this.deleted = bankAccountDoc.getBoolean("deleted", false);
	}

	// Constructor con parametros
	public BankAccount(String accountNumber, ArrayList<String> owners, double balance, Date startingDate,
			boolean deleted) {
		this.setAccountNumber(accountNumber);
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
		this.accountNumber = 
//				SpanishIBANprefix + 
				accountNumber;
	}

	public ArrayList<String> getOwners() {
		return owners;
	}

	public void setOwners(ArrayList<String> owners) {
		if (owners == null) {
			this.owners = getOwners();
			
		} else {
			this.owners = owners;
		}
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
		if (startingDate == null) {
			this.startingDate = getStartingDate();
		}else {
			this.startingDate = startingDate;
		}
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean depositMoney(double moneyIns) {
		boolean depositStatus = false;
		if (moneyIns > 0.00) {
			setBalance(getBalance() + moneyIns);
			depositStatus=true;
		} else {
			depositStatus=false;
		}
		return depositStatus;

	}

	public boolean withdrawMoney(double moneyIns) {
		boolean withdrawStatus = false;

		if (moneyIns > 0.00 && getBalance() >= moneyIns) {
			setBalance(getBalance() - moneyIns);
			withdrawStatus = true;
		} else if (moneyIns > 0.00 && moneyIns > getBalance()) {
			setBalance(0);
			withdrawStatus = true;
		} else {
			withdrawStatus = false;
		}
		return withdrawStatus;
	}

	@Override
	public String toString() {
		return "BankAccount [accountNumber=" + accountNumber + ", owners=" + owners + ", balance=" + balance
				+ ", startingDate=" + startingDate + ", deleted=" + deleted + "]";
	}
}