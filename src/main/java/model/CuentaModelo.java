package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class CuentaModelo {

//
//	- CuentaModelo.java
//		-Interfaz 
//		- Conexion a la base de datos 

	public ArrayList<CuentaBancaria> listadoCuentas;
	Document cuentaModeloDocumento;

	public CuentaModelo() {

	}

	// Metodo 1: Obtener todas las cuentas bancarias de nuestra base de datos de
	// Mongo.
	public ArrayList<CuentaBancaria> getCuentas() {
		listadoCuentas = new ArrayList<>();
		MongoClient mongo = new MongoClient("localhost", 27017);
		try {

			MongoDatabase database = mongo.getDatabase("banco");

			MongoCollection<Document> cuenta = database.getCollection("cuenta");

			Iterator<Document> it = cuenta.find().iterator();

			while (it.hasNext()) {
				Document cuentaDocumento = it.next();
				listadoCuentas.add(new CuentaBancaria(cuentaDocumento));
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
	// Metodo 2: Obtener cuentas bancarias por numero de cuenta

	public ArrayList<CuentaBancaria> getCuentaNumber(String numero_de_cuenta_ins) {
		listadoCuentas = new ArrayList<>();
		MongoClient mongo = new MongoClient("localhost", 27017);
		try {

			MongoDatabase database = mongo.getDatabase("banco");

			MongoCollection<Document> cuenta = database.getCollection("cuenta");

			Iterator<Document> it = cuenta.find(Filters.eq("numero_de_cuenta", numero_de_cuenta_ins)).iterator();

			while (it.hasNext()) {
				Document cuentaDocumento = it.next();
				listadoCuentas.add(new CuentaBancaria(cuentaDocumento));
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

	// Metodo 3: Obtener cuentas bancarias entre dos fechas de apertura de nuestra
	// base de datos de Mongo.
	public ArrayList<CuentaBancaria> getCuentasFecha(Date fecha_apertura_1, Date fecha_apertura_2) {
		listadoCuentas = new ArrayList<>();
		MongoClient mongo = new MongoClient("localhost", 27017);
		try {

			MongoDatabase database = mongo.getDatabase("banco");

			MongoCollection<Document> cuenta = database.getCollection("cuenta");

			Iterator<Document> it = cuenta.find(Filters.and(Filters.eq("borrada", false),
					Filters.gte("fecha_apertura", fecha_apertura_1), Filters.lt("fecha_apertura", fecha_apertura_2)))
					.iterator();

			while (it.hasNext()) {
				Document cuentaDocumento = it.next();
				listadoCuentas.add(new CuentaBancaria(cuentaDocumento));
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

	// Metodo 4: Crear una nueva cuenta bancaria

	public boolean insertNewCuenta(int numero_de_cuentaIns, ArrayList<String> titularesIns, double saldoIns) {
		listadoCuentas = new ArrayList<>();
		MongoClient mongo = new MongoClient("localhost", 27017);
		boolean estado = false;
		try {

			MongoDatabase database = mongo.getDatabase("banco");

			MongoCollection<Document> cuenta = database.getCollection("cuenta");

			cuentaModeloDocumento = new Document()
					.append("numero_de_cuenta", numero_de_cuentaIns)
					.append("titulares", Arrays.asList(titularesIns))
					.append("saldo", saldoIns)
					.append("fecha_apertura", new Date())
					.append("borrada", false);

			cuenta.insertOne(cuentaModeloDocumento);
			estado = true;

		} catch (Exception e) {
			estado = false;
			e.printStackTrace();
		} finally {

			if (mongo != null) {
				mongo.close();
			}
		}
		return estado;
	}

	// Metodo 5: Actualizar datos cuenta bancaria por numero de cuenta.
	public boolean updateCuenta(int numero_de_cuentaIns, ArrayList<String> titularesIns, String fecha_aperturaIns,
			boolean borradoIns) {
		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase database = mongo.getDatabase("banco");
		MongoCollection<Document> cuenta = database.getCollection("cuenta");
		boolean estado = false;

		try {
			Iterator it = cuenta.find(Filters.eq("numero_de_cuenta", numero_de_cuentaIns)).iterator();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date parsedDate = dateFormat.parse(fecha_aperturaIns);

			SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			String finalDateFormat = outputDateFormat.format(parsedDate);

			Date finalParsedDate = outputDateFormat.parse(finalDateFormat);

			cuenta.updateOne(Filters.eq("numero_de_cuenta", numero_de_cuentaIns),
					Updates.set("fecha_apertura", finalParsedDate));
			cuenta.updateOne(Filters.eq("numero_de_cuenta", numero_de_cuentaIns),
					Updates.set("titulares", titularesIns));
			cuenta.updateOne(Filters.eq("numero_de_cuenta", numero_de_cuentaIns), Updates.set("borrado", borradoIns));

			estado = true;
		} catch (Exception e) {
			estado = false;
			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
		return estado;
	}

	// Metodo 6: Borrar cuenta bancaria por numero de cuenta.

	public boolean deleteCuenta(int numero_de_cuentaIns, ArrayList<String> titularesIns, String fecha_aperturaIns,
			boolean borradoIns) {
		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase database = mongo.getDatabase("banco");
		MongoCollection<Document> cuenta = database.getCollection("cuenta");
		boolean estado = false;

		try {
			Iterator it = cuenta.find(Filters.eq("numero_de_cuenta", numero_de_cuentaIns)).iterator();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date parsedDate = dateFormat.parse(fecha_aperturaIns);

			SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			String finalDateFormat = outputDateFormat.format(parsedDate);

			Date finalParsedDate = outputDateFormat.parse(finalDateFormat);

			cuenta.updateOne(Filters.eq("numero_de_cuenta", numero_de_cuentaIns),
					Updates.set("fecha_apertura", finalParsedDate));
			cuenta.updateOne(Filters.eq("numero_de_cuenta", numero_de_cuentaIns),
					Updates.set("titulares", titularesIns));
			cuenta.updateOne(Filters.eq("numero_de_cuenta", numero_de_cuentaIns), Updates.set("borrado", borradoIns));

			estado = true;
		} catch (Exception e) {
			estado = false;
			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
		return estado;
	}

	// Metodo 7: Ingresar dinero en cuenta bancaria.

	public String insertIngresarCuenta(String numero_de_cuentaIns, double saldoIns) {
		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase database = mongo.getDatabase("banco");
		MongoCollection<Document> cuenta = database.getCollection("cuenta");
		String estado = "";
		try {
			ArrayList<CuentaBancaria> cuentasNumber = getCuentaNumber(numero_de_cuentaIns);
			double saldoPrevio = cuentasNumber.get(0).getSaldo();
			double saldo_actualizado = saldoIns + saldoPrevio;
			
			if (saldoIns >= 0.00) {
				estado = "Se ha realizado un ingreso por la cantidad de " + saldoIns + " euros." + "\n"
						+ "El saldo total de la cuenta con número de cuenta " + numero_de_cuentaIns + " es de "
						+ saldo_actualizado;
				cuenta.updateOne(Filters.eq("numero_de_cuenta", numero_de_cuentaIns),
						Updates.set("saldo", saldo_actualizado));
			} else {
				estado = "Por favor, inserte una cantidad válida para ingresar.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			estado = "No ha podido realizarse el ingreso deseado.";
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
		return estado;
	}

	// Metodo 8: Retirar dinero de una cuenta.

	public String withdrawDineroCuenta(String numero_de_cuentaIns, double saldoIns) {
		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase database = mongo.getDatabase("banco");
		MongoCollection<Document> cuenta = database.getCollection("cuenta");
		String estado = "";
		try {
			ArrayList<CuentaBancaria> cuentasNumber = getCuentaNumber(numero_de_cuentaIns);
			double saldoPrevio = cuentasNumber.get(0).getSaldo();
			double saldo_actualizado = saldoPrevio - saldoIns;

			if (saldoIns > 0.00 && saldo_actualizado > 0) {
				estado = "Se ha realizado un retiro por la cantidad de " + saldoIns + " euros." + "\n"
						+ "El saldo total de la cuenta con número de cuenta " + numero_de_cuentaIns + " es de "
						+ saldo_actualizado;
				cuenta.updateOne(Filters.eq("numero_de_cuenta", numero_de_cuentaIns),
						Updates.set("saldo", saldo_actualizado));
			} else if (saldoIns > 0.00 && saldo_actualizado < 0) {
				saldo_actualizado = 0.0;
				estado = "El saldo de la cuenta es de" + saldo_actualizado + " euros.";
				cuenta.updateOne(Filters.eq("numero_de_cuenta", numero_de_cuentaIns),
						Updates.set("saldo", saldo_actualizado));
			} else {
				estado = "Por favor, inserte una cantidad válida para retirar.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			estado = "No ha podido realizarse el retiro deseado.";
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
		return estado;
	}

	public static void main(String[] args) {
		CuentaModelo cuenta = new CuentaModelo();
		cuenta.withdrawDineroCuenta("111111111", 313131313.0);
	}
}
