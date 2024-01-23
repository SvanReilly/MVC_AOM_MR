package com.bank.MVC_AOM_MR.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;

public class CuentaModelo {
//	- CuentaModelo.java
//		-Interfaz 
//		- Conexion a la base de datos 
	CuentaBancaria cuentaBancaria;
	ArrayList<CuentaBancaria> listadoCuentas;
	ArrayList<CuentaBancaria> cuentasNumber;
	ArrayList<Document> cuentasBancariasDoc;
	
	Document cuentaModeloDocumento;
	
	MongoClient mongo;
	MongoDatabase database;
	MongoCollection<Document> cuenta;
	Iterator<Document> it;

	double saldoPrevio;
	double saldo_actualizado;
	boolean estado_boolean = false;
	String estado_string = "";
	SimpleDateFormat dateFormat;
	Date parsedDate1, parsedDate2;
	SimpleDateFormat outputDateFormat;
	String finalDateFormat;
	Date finalParsedDate1, finalParsedDate2;

	public CuentaModelo() {
	}
	
	public void mongoConnection() {
		mongo = new MongoClient("localhost", 27017);
		database = mongo.getDatabase("banco");
		cuenta = database.getCollection("cuenta");
	}

	// Metodo 1: Obtener todas las cuentas bancarias de nuestra bbdd // Working
	public ArrayList<CuentaBancaria> getAllAccounts() {
		listadoCuentas = new ArrayList<>();
		
		try {

			mongoConnection();

			it = cuenta.find(Filters.eq("borrada", false)).iterator();

			while (it.hasNext()) {
				cuentaModeloDocumento = it.next();
				cuentaBancaria = new CuentaBancaria(cuentaModeloDocumento);
				listadoCuentas.add(cuentaBancaria);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (mongo != null) {
				mongo.close();
			}
		}

		return listadoCuentas;
	}
	// Metodo 2: Obtener cuentas bancarias por numero de cuenta // Working

	public CuentaBancaria getAccountPerNumber(String numero_de_cuentaIns) {
		
		try {
			mongoConnection();
			it = cuenta.find(Filters.eq("numero_de_cuenta", numero_de_cuentaIns)).iterator();
			cuentaModeloDocumento = it.next();
			cuentaBancaria= new CuentaBancaria(cuentaModeloDocumento);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
		return cuentaBancaria;
	}

	// Metodo 3: Obtener cuentas bancarias entre dos fechas. // Working
	public ArrayList<CuentaBancaria> getAccountPerDate(
			Date fecha_apertura_1, 
			Date fecha_apertura_2) {
		listadoCuentas = new ArrayList<>();
	
		try {
			mongoConnection();
			it = cuenta
					.find(Filters.and(Filters.eq("borrada", false), Filters.gte("fecha_de_apertura", fecha_apertura_1),
							Filters.lt("fecha_de_apertura", fecha_apertura_2)))
					.sort(Sorts.descending("fecha_de_apertura")).iterator();
			while (it.hasNext()) {
				cuentaModeloDocumento = it.next();
				listadoCuentas.add(new CuentaBancaria(cuentaModeloDocumento));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (mongo != null) {
				mongo.close();
			}
		}

		return listadoCuentas;
	}

	// Metodo 4: Crear una nueva cuenta bancaria // Working

	public boolean insertNewAccount(CuentaBancaria bankAccountModel) {
		try {
			mongoConnection();
				cuentaModeloDocumento = new Document()
						.append("account_number", bankAccountModel.getAccountNumber())
						.append("owners", bankAccountModel.getOwners())
						.append("balance", bankAccountModel.getBalance())
						.append("starting_date", new Date())
						.append("deleted", false);
				
				cuenta.insertOne(cuentaModeloDocumento);
				estado_boolean = true;
								
		} catch (Exception e) {
			estado_boolean = false;
			e.printStackTrace();
		} finally {

			if (mongo != null) {
				mongo.close();
			}
		}
		return estado_boolean;
	}

	// Metodo 5: Actualizar datos cuenta bancaria por numero de cuenta. // Test Pending*
	// Pending
	public boolean updateAccount(CuentaBancaria bankAccountModel) {
		estado_boolean = false;
		try {
			mongoConnection();

			cuenta.updateOne(Filters.eq("numero_de_cuenta", bankAccountModel.getAccountNumber()),
					Updates.set("titulares", bankAccountModel.getOwners()));

			cuenta.updateOne(Filters.eq("numero_de_cuenta", bankAccountModel.getAccountNumber()),
					Updates.set("fecha_de_apertura", bankAccountModel.getStartingDate()));
			
			cuenta.updateOne(Filters.eq("numero_de_cuenta", bankAccountModel.getAccountNumber()),
					Updates.set("saldo", bankAccountModel.getBalance()));

			cuenta.updateOne(Filters.eq("numero_de_cuenta", bankAccountModel.getAccountNumber()), 
					Updates.set("borrada", bankAccountModel.isDeleted()));

			estado_boolean = true;
		} catch (Exception e) {
			estado_boolean = false;
			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
		return estado_boolean;
	}

	// Metodo 6: Borrar cuenta bancaria por numero de cuenta. // Working

//	public boolean deleteAccount(String numero_de_cuentaIns) {
//		mongo = new MongoClient("localhost", 27017);
//		database = mongo.getDatabase("banco");
//		cuenta = database.getCollection("cuenta");
//		estado_boolean = false;
//
//		try {
//			cuenta.deleteMany(Filters.eq("numero_de_cuenta", numero_de_cuentaIns));
//			estado_boolean = true;
//		} catch (Exception e) {
//			estado_boolean = false;
//			e.printStackTrace();
//		} finally {
//			if (mongo != null) {
//				mongo.close();
//			}
//		}
//		return estado_boolean;
//	}

	// Metodo 7: Ingresar dinero en cuenta bancaria. // Working

//	public String depositMoney(String numero_de_cuentaIns, double saldo_entrante) {
//		mongo = new MongoClient("localhost", 27017);
//		database = mongo.getDatabase("banco");
//		cuenta = database.getCollection("cuenta");
//		estado_string = "";
//		try {
////			cuentasNumber = getCuentaNumber(numero_de_cuentaIns);
//			saldoPrevio = cuentasNumber.get(0).getBalance();
//			saldo_actualizado = saldo_entrante + saldoPrevio;
//
//			if (saldo_entrante >= 0.00) {
//				estado_string = "Se ha realizado un ingreso por la cantidad de " + saldo_entrante + " euros." + "\n"
//						+ "El saldo total de la cuenta con numero de cuenta " + numero_de_cuentaIns + " es de "
//						+ saldo_actualizado;
//				cuenta.updateOne(Filters.eq("numero_de_cuenta", numero_de_cuentaIns),
//						Updates.set("saldo", saldo_actualizado));
//			} else {
//				estado_string = "Por favor, inserte una cantidad valida para ingresar.";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			estado_string = "No ha podido realizarse el ingreso deseado.";
//		} finally {
//			if (mongo != null) {
//				mongo.close();
//			}
//		}
//		return estado_string;
//	}

	// Metodo 8: Retirar dinero de una cuenta. // Working

	public String withdrawMoney(String numero_de_cuentaIns, double saldo_saliente) {
		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase database = mongo.getDatabase("banco");
		MongoCollection<Document> cuenta = database.getCollection("cuenta");
		estado_string = "";
		try {
//			ArrayList<CuentaBancaria> cuentasNumber = getCuentaNumber(numero_de_cuentaIns);
			double saldoPrevio = cuentasNumber.get(0).getBalance();
			double saldo_actualizado = saldoPrevio - saldo_saliente;

			if (saldo_saliente > 0.00 && saldo_actualizado > 0) {
				estado_string = "Se ha realizado un retiro por la cantidad de " + saldo_saliente + " euros." + "\n"
						+ "El saldo total de la cuenta con numero de cuenta " + numero_de_cuentaIns + " es de "
						+ saldo_actualizado;
				cuenta.updateOne(Filters.eq("numero_de_cuenta", numero_de_cuentaIns),
						Updates.set("saldo", saldo_actualizado));
			} else if (saldo_saliente > 0.00 && saldo_actualizado < 0) {
				saldo_actualizado = 0.0;
				estado_string = "El saldo de la cuenta es de" + saldo_actualizado + " euros.";
				cuenta.updateOne(Filters.eq("numero_de_cuenta", numero_de_cuentaIns),
						Updates.set("saldo", saldo_actualizado));
			} else {
				estado_string = "Por favor, inserte una cantidad valida para retirar.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			estado_string = "No ha podido realizarse el retiro deseado.";
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
		return estado_string;
	}

}
