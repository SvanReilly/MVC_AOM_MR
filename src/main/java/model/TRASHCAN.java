package model;

import java.util.Arrays;
import java.util.Date;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TRASHCAN {
	public static void main(String[] args) {

		
		

        // Configura la conexión a MongoDB
        MongoClient mongo = new MongoClient("localhost", 27017);

        // Acceder a la base de datos
        MongoDatabase database = mongo.getDatabase("banco");

        for (String name : database.listCollectionNames()) {
            System.out.println(name);
        }
        
        MongoCollection<Document> cuenta = database.getCollection("cuenta");
        
//        // Generar el iterador para mostrar todos los datos de la base de datos
//        Iterator<Document> it = cuenta.find().iterator();
//        while (it.hasNext()) {
//            System.out.println(it.next());
//        }
        
        
     // Insertar datos en la colección
        Document document = new Document()
                .append("numero_de_cuenta", "1234567890")
                .append("titulares", Arrays.asList("Miguel Ángel", "Alex"))
                .append("saldo", 50)
                .append("fecha_apertura", new Date())
                .append("borrada", false);

//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        try {
//            Date fechaApertura = dateFormat.parse("2024-05-05T10:10:10.100Z");
//            document.append("fecha_apertura", fechaApertura);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        cuenta.insertOne(document);

//        // Borrar datos de un documento
//        cuenta.deleteMany(Filters.eq("titulares", "Juan Pérez"));
//
//        // Actualizar datos
//        cuenta.updateOne(
//                Filters.eq("numero_de_cuenta", "5555555555"),
//                Updates.set("saldo", 500)
//        );

        // Cerrar la conexión a MongoDB al finalizar
        mongo.close();
	}
}
