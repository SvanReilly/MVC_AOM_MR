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
//		-Interfaz 
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

	public void mongoDisconnection() {
		mongo.close();
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
			e.printStackTrace();
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
		try {
//			it = accountCollections.find(Filters.eq("account_number", accountNumberIns)).iterator();
			ModelAccountDocument = accountCollections.find(Filters.eq("account_number", accountNumberIns)).first();
			bankAccount = new BankAccount(ModelAccountDocument);
		} catch (Exception e) {
			e.printStackTrace();
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
	public ArrayList<BankAccount> getAccountPerDate(Date startingDateIns_1, Date startingDateIns_2) {
		accountList = new ArrayList<>();

		try {
			mongoConnection();
			it = accountCollections
					.find(Filters.and(Filters.eq("deleted", false), Filters.gte("starting_date", startingDateIns_1),
							Filters.lt("starting_date", startingDateIns_2)))
					.sort(Sorts.descending("starting_date")).iterator();
			while (it.hasNext()) {
				ModelAccountDocument = it.next();
				accountList.add(new BankAccount(ModelAccountDocument));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (mongo != null) {
				mongo.close();
			}
		}

		return accountList;
	}

	// Metodo 4: Crear una nueva cuenta bancaria // Working

	public boolean insertNewAccount(Document ModelAccountDocumentIns) {
		try {
			mongoConnection();
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
	@SuppressWarnings("unchecked")
	public boolean updateAccount(Document ModelAccountDocumentIns) {
		boolean_status = false;
		try {
			mongoConnection();

			accountCollections.updateOne(Filters.eq("account_number", ModelAccountDocumentIns.getString("account_number")),
					Updates.set("owners", (ArrayList<String>) ModelAccountDocumentIns.get("owners")));
			
			accountCollections.updateOne(Filters.eq("account_number", ModelAccountDocumentIns.getString("account_number")),
					Updates.set("balance", (double) ModelAccountDocumentIns.get("balance")));
			
			accountCollections.updateOne(Filters.eq("account_number", ModelAccountDocumentIns.get("account_number")),
					Updates.set("fecha_de_apertura", ModelAccountDocumentIns.getDate("starting_date")));
			
			accountCollections.updateOne(Filters.eq("account_number", ModelAccountDocumentIns.getString("account_number")),
					Updates.set("deleted", ModelAccountDocumentIns.getBoolean("deleted")));

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
}
