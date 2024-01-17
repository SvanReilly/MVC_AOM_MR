package model;

import java.text.ParseException;
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
import com.mongodb.client.model.Updates;

public class CuentaModelo {

//
//	- CuentaModelo.java
//		-Interfaz 
//		- Conexion a la base de datos 

	public ArrayList<CuentaBancaria> listadoCuentas;

	public CuentaModelo() {

	}

	// Método 1: Obtener todas las cuentas bancarias de nuestra base de datos de
	// Mongo.
	public ArrayList<CuentaBancaria> getCuentas() {
		ArrayList<CuentaBancaria> listadoCuentas = new ArrayList<>();
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

	// Método 2: obtener cuentas bancarias entre dos fechas de apertura de nuestra
	// base de datos de Mongo.
	public ArrayList<CuentaBancaria> getCuentasFecha(Date fecha_apertura_1, Date fecha_apertura_2) {
		ArrayList<CuentaBancaria> listadoCuentas = new ArrayList<>();
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

	public static void main(String[] args) {
		CuentaModelo cuenta = new CuentaModelo();
		cuenta.getCuentas();
	}
}
