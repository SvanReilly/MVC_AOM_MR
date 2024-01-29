package com.bank.MVC_AOM_MR.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;

public class ModelAccount {
//	- CuentaModelo.java
//		- Interfaz 
//		- Conexion a la base de datos 
	
	BankAccount bankAccount;
	ArrayList<BankAccount> accountList;

	Document ModelAccountDocument;
	
	MongoClient mongo;
	MongoDatabase database;
	MongoCollection<Document> accountCollections;
	Iterator<Document> it;
	boolean boolean_status = false;
	String estado_string = "";

	SimpleDateFormat dateFormat, outputDateFormat;
	Date parsedDate1, parsedDate2;
	String finalDateFormat;
	Date startingDateOldParsed, startingDateRecentParsed;

	public ModelAccount() {
	}

	public void mongoConnection() {
		mongo = new MongoClient("localhost", 27017);
		database = mongo.getDatabase("bank");
		accountCollections = database.getCollection("account");
	}

	// EXTRA EXTRA EXTRA //
	public boolean deleteDataBase() {
		boolean_status= false;
		mongoConnection();
		if(boolean_status == false) {
			database.drop();
			boolean_status = true;
		} 
		return boolean_status;
	}

	// Metodo 1: Obtener todas las cuentas bancarias de nuestra bbdd // Working
	public ArrayList<BankAccount> getAllAccounts() {
		mongoConnection();
		accountList = new ArrayList<BankAccount>();
		try {
			it = accountCollections
					.find(Filters.eq("deleted", false))
					.sort(Sorts.descending("starting_date")).iterator();
			while (it.hasNext()) {
				ModelAccountDocument = it.next();
				bankAccount = new BankAccount(ModelAccountDocument);
				accountList.add(bankAccount);
			}
		} catch (Exception e) {
//			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
		return accountList;
	}
	// Metodo 2: Obtener cuentas bancarias por numero de cuenta // Working

	public BankAccount getAccountPerNumber(String accountNumberIns) {
		mongoConnection();
		bankAccount = new BankAccount();
		try {
//			it = accountCollections.find(Filters.eq("account_number", accountNumberIns)).iterator();
			ModelAccountDocument = accountCollections
					.find(Filters.eq("account_number", accountNumberIns)).first();
			bankAccount = new BankAccount(ModelAccountDocument);
		} catch (Exception e) {
//			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
		return bankAccount;
	}
	

	// Metodo 3: Obtener cuentas bancarias entre dos fechas. // Working
	public ArrayList<BankAccount> getAccountPerDate(Date startingDateOld, Date startingDateRecent) {
		mongoConnection();
		accountList = new ArrayList<BankAccount>();
		try {
//			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//			parsedDate1 = dateFormat.parse(startingDateOld);
//			parsedDate2 = dateFormat.parse(startingDateRecent);
//
//			outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//
//			finalDateFormat = outputDateFormat.format(parsedDate1);
//			startingDateOldParsed =  (Date) outputDateFormat.parse(finalDateFormat);
//
//			finalDateFormat = outputDateFormat.format(parsedDate2);
//			startingDateRecentParsed = (Date) outputDateFormat.parse(finalDateFormat);
//
//			System.out.println(startingDateOldParsed+ "\n" + startingDateRecentParsed);
//			
			it = accountCollections
					.find(Filters.and(
							Filters.eq("deleted", false), 
							Filters.gte("starting_date", startingDateOld),
							Filters.lte("starting_date", startingDateRecent)))
					.sort(Sorts.descending("starting_date")).iterator();
			while (it.hasNext()) {
				ModelAccountDocument = it.next();
				bankAccount = new BankAccount(ModelAccountDocument);
				accountList.add(bankAccount);
			}
		} catch (Exception e) {
//			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
		return accountList;
	}
//	public static void main(String[] args) {
//	ModelAccount modelAccount = new ModelAccount();
//	System.out.println("");
//	System.out.println(modelAccount.getAccountPerDate("1500-06-24", "2025-09-23"));
//}

	// Metodo 4: Crear una nueva cuenta bancaria // Working

	public boolean insertNewAccount(BankAccount bankAccount) {
		
		try {
			mongoConnection();
			
			Document ModelAccountDocumentIns = new Document()
					.append("account_number", bankAccount.getAccountNumber())
					.append("owners", bankAccount.getOwners())
					.append("balance", bankAccount.getBalance())
					.append("starting_date", new Date())
					.append("deleted", false);
			
			accountCollections.insertOne(ModelAccountDocumentIns);
			boolean_status = true;
		} catch (Exception e) {
			boolean_status = false;
			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
		return boolean_status;
	}

	// Metodo 5: Actualizar datos cuenta bancaria por numero de cuenta. // Test
	// Pending*
	// Pending
	public boolean updateAccount(BankAccount bankAccount) {
		boolean_status = false;
		try {
			mongoConnection();

			accountCollections.updateOne(Filters.eq("account_number", bankAccount.getAccountNumber()),
					Updates.set("owners", bankAccount.getOwners()));
			
			accountCollections.updateOne(Filters.eq("account_number", bankAccount.getAccountNumber()),
					Updates.set("balance", bankAccount.getBalance()));
			
			accountCollections.updateOne(Filters.eq("account_number", bankAccount.getAccountNumber()),
					Updates.set("starting_date", bankAccount.getStartingDate()));
			
			accountCollections.updateOne(Filters.eq("account_number", bankAccount.getAccountNumber()),
					Updates.set("deleted", bankAccount.isDeleted()));

			boolean_status = true;
		} catch (Exception e) {
			boolean_status = false;
//			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
		return boolean_status;
	}
	

}
