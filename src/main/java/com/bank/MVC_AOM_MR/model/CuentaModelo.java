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

//
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

	// Metodo 1: Obtener todas las cuentas bancarias de nuestra bbdd // Working
	public ArrayList<CuentaBancaria> getCuentas() {
		listadoCuentas = new ArrayList<>();
		mongo = new MongoClient("localhost", 27017);
		try {

			database = mongo.getDatabase("banco");

			cuenta = database.getCollection("cuenta");

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

	public ArrayList<CuentaBancaria> getCuentaNumber(String numero_de_cuenta_ins) {
		listadoCuentas = new ArrayList<>();
		mongo = new MongoClient("localhost", 27017);
		try {

			database = mongo.getDatabase("banco");

			cuenta = database.getCollection("cuenta");

			it = cuenta.find(Filters.eq("numero_de_cuenta", numero_de_cuenta_ins))
					.sort(Sorts.descending("fecha_apertura")).iterator();

			while (it.hasNext()) {
				cuentaModeloDocumento = it.next();
				listadoCuentas.add(new CuentaBancaria(cuentaModeloDocumento));
				System.out.println(cuentaModeloDocumento);
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

	// Metodo 3: Obtener cuentas bancarias entre dos fechas. // Working
	public ArrayList<CuentaBancaria> getCuentasFecha(String fecha_apertura_1, String fecha_apertura_2) {
		listadoCuentas = new ArrayList<>();
		mongo = new MongoClient("localhost", 27017);
		try {

			MongoDatabase database = mongo.getDatabase("banco");

			cuenta = database.getCollection("cuenta");

			dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			parsedDate1 = dateFormat.parse(fecha_apertura_1);
			parsedDate2 = dateFormat.parse(fecha_apertura_2);

			outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

			finalDateFormat = outputDateFormat.format(parsedDate1);
			finalParsedDate1 = outputDateFormat.parse(finalDateFormat);

			finalDateFormat = outputDateFormat.format(parsedDate2);
			finalParsedDate2 = outputDateFormat.parse(finalDateFormat);

			it = cuenta
					.find(Filters.and(Filters.eq("borrada", false), Filters.gte("fecha_apertura", finalParsedDate1),
							Filters.lt("fecha_apertura", finalParsedDate2)))
					.sort(Sorts.descending("fecha_apertura")).iterator();

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

	public boolean insertNewCuenta(String numero_de_cuentaIns, ArrayList<String> titularesIns, double saldoIns) {
		cuentaBancaria = new CuentaBancaria();
		listadoCuentas = new ArrayList<CuentaBancaria>();

		mongo = new MongoClient("localhost", 27017);
		try {
			database = mongo.getDatabase("banco");

			cuenta = database.getCollection("cuenta");

			it = cuenta.find(Filters.eq("numero_de_cuenta", numero_de_cuentaIns)).iterator();

				if (!it.hasNext()) {
				cuentaModeloDocumento = new Document()
						.append("numero_de_cuenta", numero_de_cuentaIns)
						.append("titulares", Arrays.asList(titularesIns))
						.append("saldo", saldoIns)
						.append("fecha_apertura", new Date())
						.append("borrada", false);
				
				cuenta.insertOne(cuentaModeloDocumento);
					
				estado_boolean = true;
					
				} else {

				estado_boolean = false;
				
				}
			
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
	public boolean updateCuenta(String numero_de_cuentaIns, ArrayList<String> titularesIns, String fecha_aperturaIns,
			boolean borradoIns) {
		mongo = new MongoClient("localhost", 27017);
		database = mongo.getDatabase("banco");
		cuenta = database.getCollection("cuenta");
		estado_boolean = false;

		try {

			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			parsedDate1 = dateFormat.parse(fecha_aperturaIns);

			outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			finalDateFormat = outputDateFormat.format(parsedDate1);

			finalParsedDate1 = outputDateFormat.parse(finalDateFormat);

			cuenta.updateOne(Filters.eq("numero_de_cuenta", numero_de_cuentaIns),
					Updates.set("fecha_apertura", finalParsedDate1));

			cuenta.updateOne(Filters.eq("numero_de_cuenta", numero_de_cuentaIns),
					Updates.set("titulares", titularesIns));

			cuenta.updateOne(Filters.eq("numero_de_cuenta", numero_de_cuentaIns), Updates.set("borrado", borradoIns));

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

	public boolean deleteCuenta(String numero_de_cuentaIns) {
		mongo = new MongoClient("localhost", 27017);
		database = mongo.getDatabase("banco");
		cuenta = database.getCollection("cuenta");
		estado_boolean = false;

		try {
			cuenta.deleteMany(Filters.eq("numero_de_cuenta", numero_de_cuentaIns));
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

	// Metodo 7: Ingresar dinero en cuenta bancaria. // Working

	public String insertIngresarCuenta(String numero_de_cuentaIns, double saldo_entrante) {
		mongo = new MongoClient("localhost", 27017);
		database = mongo.getDatabase("banco");
		cuenta = database.getCollection("cuenta");
		estado_string = "";
		try {
//			cuentasNumber = getCuentaNumber(numero_de_cuentaIns);
			saldoPrevio = cuentasNumber.get(0).getSaldo();
			saldo_actualizado = saldo_entrante + saldoPrevio;

			if (saldo_entrante >= 0.00) {
				estado_string = "Se ha realizado un ingreso por la cantidad de " + saldo_entrante + " euros." + "\n"
						+ "El saldo total de la cuenta con numero de cuenta " + numero_de_cuentaIns + " es de "
						+ saldo_actualizado;
				cuenta.updateOne(Filters.eq("numero_de_cuenta", numero_de_cuentaIns),
						Updates.set("saldo", saldo_actualizado));
			} else {
				estado_string = "Por favor, inserte una cantidad valida para ingresar.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			estado_string = "No ha podido realizarse el ingreso deseado.";
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
		return estado_string;
	}

	// Metodo 8: Retirar dinero de una cuenta. // Working

	public String withdrawDineroCuenta(String numero_de_cuentaIns, double saldo_saliente) {
		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase database = mongo.getDatabase("banco");
		MongoCollection<Document> cuenta = database.getCollection("cuenta");
		estado_string = "";
		try {
//			ArrayList<CuentaBancaria> cuentasNumber = getCuentaNumber(numero_de_cuentaIns);
			double saldoPrevio = cuentasNumber.get(0).getSaldo();
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
