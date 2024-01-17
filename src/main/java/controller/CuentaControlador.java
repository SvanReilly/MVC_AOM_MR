package controller;

// controlador --> Objeto: cuentaControlador.java
//			- Rutas de las api
//			- Get...Put..
//	. Mandan Objetos entre controlador y modelo 
import java.util.ArrayList;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.CuentaBancaria;
import model.CuentaModelo;

@RestController
@RequestMapping
public class CuentaControlador {
	CuentaModelo cuentaModelo = new CuentaModelo();
	// Mostrar todas las cuentas bancarias: /banco/cuenta (GET) (No debe mostrar las cuentas borradas):
	
	@GetMapping("/banco/cuenta/")
	public ArrayList<CuentaBancaria> getCuentas() {
		
		return null ;
		
	}
	
	// Mostrar cuentas bancarias por número de cuenta: /banco/cuenta/{nro_cuenta} (GET):
	

    @GetMapping("/banco/cuenta/{nro_cuenta}")
    public int getCuentasNum(@PathVariable int numero1, @PathVariable int numero2) {
        return  numero1 * numero2;
    }
    
    // Mostrar cuentas bancarias por rango de fecha de apertura: /banco/cuenta/{fecha_ini}/{fecha_fin} (GET):
    
     @GetMapping("/banco/cuenta/{fecha_ini}/{fecha_fin}")
     public boolean mayorEdad(@PathVariable int edad) {
    	 
    	 boolean mayorEdadBool;
    	 if (edad < 18) {
    		 mayorEdadBool= false;
    	 }else {
    		 
             mayorEdadBool = true;
     }
    	 return mayorEdadBool;
     }
     
     
    // Insertar nuevas cuentas bancarias: /banco/cuenta/new (PUT):
         @PutMapping("/banco/cuenta/new")
         public boolean crearCuenta(@PathVariable String nombre) {
			boolean estado = false;
        	 return false;
         }
     
    // Actualizar cuentas bancarias por número de cuenta: /banco/cuenta/update (PUT):
         @GetMapping("/banco/cuenta/update")
     	public ArrayList<Integer> listaNumeros(@PathVariable int n) {
        	 ArrayList<Integer> lista = new ArrayList<Integer>();
        	 for (int i = 1; i <= n; i++) {
        		lista.add(i);
			}
			return lista;
        	
     		
     	}
         
         
         @PostMapping("/sumaPost")
     	public int sumaPost(@RequestParam int numero1, @RequestParam int numero2) {
     		return numero1+numero2;
     	}
         
         // Borrar cuentas bancarias por numero de cuenta: /banco/cuenta/{nro_cuenta} (DELETE) (soft deletion):
         @DeleteMapping("/banco/cuenta/{numeroDeCuenta}")
         public boolean borraCuentaBancaria(@RequestParam String numeroDeCuenta) {
        	 boolean mayorEdadBool=true;
        	
        	 return mayorEdadBool;
         }

         
         // Actualizar cuentas bancarias por número de cuenta: /banco/cuenta/update (PUT):
         @PutMapping("/banco/cuenta/update")
         public boolean mayorEdadPut(@RequestParam int edad) {
        	 boolean mayorEdadBool;
        	 if (edad < 18) {
        		 mayorEdadBool= false;
        	 }else {
        		 
                 mayorEdadBool = true;
         }
        	 return mayorEdadBool;
         }


//         Insertar dinero por numero de cuenta: /banco/cuenta/ingresar/ (PUT con datos {nro_cuenta} e {ingreso})
//         Devuelve un string con el saldo ingresado y el saldo restante.
//         Debe actualizar el saldo.
//         Si el dinero a ingresar es negativo, no hace nada.
         
         @PutMapping("/banco/cuenta/ingresar/")
         public boolean ingresarDinero(@RequestParam int edad) {
        	 boolean mayorEdadBool;
        	 if (edad < 18) {
        		 mayorEdadBool= false;
        	 }else {
        		 
                 mayorEdadBool = true;
         }
        	 return mayorEdadBool;
         }
         
//         Retirar dinero por numero de cuenta: /banco/cuentas/retirar/ (PUT con datos {nro_cuenta} y {retiro})
//         Devuelve un string con el saldo retirado y el saldo restante.
//         Debe actualizar el saldo.
//         Si el dinero a retirar es negativo no hace nada.
//         Si el saldo de la cuenta se queda a 0, solo debe retirar el saldo restante.
         
         @PutMapping("/banco/cuenta/retirar/")
         public boolean retirarDinero(@RequestParam int edad) {
        	 boolean mayorEdadBool;
        	 if (edad < 18) {
        		 mayorEdadBool= false;
        	 }else {
        		 
                 mayorEdadBool = true;
         }
        	 return mayorEdadBool;
         }
}
//

