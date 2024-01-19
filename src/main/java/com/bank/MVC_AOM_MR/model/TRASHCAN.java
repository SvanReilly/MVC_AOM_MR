package com.bank.MVC_AOM_MR.model;

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

public class TRASHCAN {
	
		
		public boolean insertNewCuenta(String numero_de_cuentaIns, 
				ArrayList<String> titularesIns, double saldoIns) {
			ArrayList<String> listadoCuentas = new ArrayList<>();
			MongoClient mongo = new MongoClient("localhost", 27017);
			boolean estado_boolean = false;
			try {

			MongoDatabase	database = mongo.getDatabase("banco");

				MongoCollection cuenta = database.getCollection("cuenta");
				
				Iterator<Document> it = cuenta.find(Filters.eq("numero_de_cuenta", numero_de_cuentaIns))
						.iterator();
				System.out.println(it);
//				if ( it == null ) {
//					cuentaModeloDocumento = new Document()
//							.append("numero_de_cuenta", numero_de_cuentaIns)
//							.append("titulares", Arrays.asList(titularesIns))
//							.append("saldo", saldoIns)
//							.append("fecha_apertura", new Date())
//							.append("borrada", false);
//					
//					cuenta.insertOne(cuentaModeloDocumento);
//					estado_boolean = true;
//				} else {
//					
//					
//					
//					estado_boolean=false;
//				}
				
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

		
	
		public static void main(String[] args) throws ParseException {

			
		}}

