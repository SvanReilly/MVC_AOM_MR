package com.bank.MVC_AOM_MR.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

public class CuentaBancaria {
	private String accountNumber;
	private ArrayList<String> owners;
	private double balance;
	private Date startingDate;
	private boolean deleted;

	// Constructor por defecto
	public CuentaBancaria() {
		this.accountNumber= "";
		this.owners = new ArrayList<>();
		this.balance= 0;
		this.startingDate = new Date();
		this.deleted = false;
	}

	public CuentaBancaria(Document bankAccountDoc) {
		this.accountNumber = bankAccountDoc.getString("numero_de_cuenta");
		this.owners = (ArrayList<String>) bankAccountDoc.get("titulares");
		this.balance = bankAccountDoc.get("saldo", Number.class).doubleValue();
		this.startingDate = (Date) bankAccountDoc.getDate("fecha_de_apertura");
		this.deleted = bankAccountDoc.getBoolean("borrada", false);
	}

	// Constructor con parámetros
	public CuentaBancaria(String accountNumber, ArrayList<String> owners, double balance, Date startingDate,
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

	public void setTitulares(ArrayList<String> owners) {
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
	
	public void depositMoney (double moneyIns) {
		setBalance(getBalance() + moneyIns);
	}
	public void withdrawMoney (double moneyIns) {
		setBalance(getBalance() - moneyIns);
	}


}
