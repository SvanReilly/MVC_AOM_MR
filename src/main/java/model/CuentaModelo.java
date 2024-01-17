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
				System.out.println(cuentaDocumento);
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
	
	public ArrayList<CuentaBancaria> getCuentasNumber(String numero_de_cuenta_ins) {
		listadoCuentas = new ArrayList<>();
		MongoClient mongo = new MongoClient("localhost", 27017);
		try {

			MongoDatabase database = mongo.getDatabase("banco");

			MongoCollection<Document> cuenta = database.getCollection("cuenta");

			Iterator<Document> it = cuenta.find(Filters.eq("numero_de_cuenta", numero_de_cuenta_ins))
					.iterator();

			while (it.hasNext()) {
				Document cuentaDocumento = it.next();
				listadoCuentas.add(new CuentaBancaria(cuentaDocumento));
				System.out.println(cuentaDocumento);
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
				System.out.println(cuentaDocumento);
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
	
	public boolean insertNewCuenta() {
		listadoCuentas = new ArrayList<>();
		MongoClient mongo = new MongoClient("localhost", 27017);
		boolean estado = false;
		try {

			MongoDatabase database = mongo.getDatabase("banco");

			MongoCollection<Document> cuenta = database.getCollection("cuenta");

			cuentaModeloDocumento = new Document()
	                .append("numero_de_cuenta", "1234567890")
	                .append("titulares", Arrays.asList("Perro Sanche", "Jeffrey Epstein"))
	                .append("saldo", 550000000000000000.00)
	                .append("fecha_apertura", new Date())
	                .append("borrada", false);
					
			cuenta.insertOne(cuentaModeloDocumento);
			estado =true;

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
	public boolean updateNewCuenta(String numero_de_cuentaIns, List<String> titularesIns, 
			double saldoIns, Date fechaAperturaIns, boolean borradaIns) {
		listadoCuentas = new ArrayList<>();
		MongoClient mongo = new MongoClient("localhost", 27017);
		boolean estado = false;
		try {

			MongoDatabase database = mongo.getDatabase("banco");

			MongoCollection<Document> cuenta = database.getCollection("cuenta");

			SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaAperturaStr;
			Date fechaApertura = inputDateFormat.parse(fechaAperturaStr);

            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            String fechaFormateada = outputDateFormat.format(fechaApertura);
			
			cuenta.updateOne(Filters.eq("fecha_de_apertura", numero_de_cuentaIns), 
					Updates.set("saldo", saldoIns), Updates.set(numero_de_cuentaIns, null));
			
			estado =true;

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
	
	// Metodo 7: Ingresar dinero  en cuenta bancaria.
	
	// Metodo 8: Obtener cuentas bancarias entre dos fechas de apertura de nuestra
	// base de datos de Mongo.
	
	public static void main(String[] args) {
		CuentaModelo cuenta = new CuentaModelo();
		
		cuenta.getCuentas();
	}
}
