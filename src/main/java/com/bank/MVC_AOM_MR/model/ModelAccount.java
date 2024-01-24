package com.bank.MVC_AOM_MR.model;

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


	public ModelAccount() {
	}

	public void mongoConnection() {
		mongo = new MongoClient("localhost", 27017);
		database = mongo.getDatabase("bank");
		accountCollections = database.getCollection("account");
	}


	// Metodo 1: Obtener todas las cuentas bancarias de nuestra bbdd // Working
	public ArrayList<BankAccount> getAllAccounts() {
		accountList = new ArrayList<BankAccount>();
		mongoConnection();
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
	
//	public static void main(String[] args) {
//		ModelAccount modelAccount = new ModelAccount();
//		System.out.println(modelAccount.getAccountPerNumber("jejejejrickroll4324").toString());
//	}

	// Metodo 3: Obtener cuentas bancarias entre dos fechas. // Working
	public ArrayList<BankAccount> getAccountPerDate(Date startingDateOld, Date startingDateRecent) {
		accountList = new ArrayList<>();

		try {
			mongoConnection();
			it = accountCollections
					.find(Filters.and(
							Filters.eq("deleted", false), 
							Filters.gte("startingDate", startingDateOld),
							Filters.lt("startingDate", startingDateRecent)))
					.sort(Sorts.descending("starting_date")).iterator();
			while (it.hasNext()) {
				ModelAccountDocument = it.next();
				accountList.add(new BankAccount(ModelAccountDocument));
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
