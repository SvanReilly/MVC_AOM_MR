package com.bank.MVC_AOM_MR.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.MVC_AOM_MR.model.CuentaBancaria;
import com.bank.MVC_AOM_MR.model.CuentaModelo;

@RestController
public class CuentaControlador {
	CuentaModelo cuentaModelo = new CuentaModelo();

	@GetMapping("/hi")
	public String hi() {
		String hiStr = "Hello, world!";
		return hiStr;
	}
	// Mostrar todas las cuentas bancarias: /banco/cuenta (GET) (No debe mostrar las
	// cuentas borradas):
	@GetMapping("/banco/cuenta")
	public List<CuentaBancaria> getCuentasController() {
		return cuentaModelo.getCuentas();
	}
//
//	// Mostrar cuentas bancarias por numero de cuenta: /banco/cuenta/{nro_cuenta}
//	// (GET):
//	@GetMapping("banco/cuenta/{nro_cuenta}")
//	public ArrayList<CuentaBancaria> getCuentaNumberController(@PathVariable String nro_cuenta) {
//		return cuentaModelo.getCuentaNumber(nro_cuenta);
//	}
//
//	// Mostrar cuentas bancarias por rango de fecha de apertura:
//	// /banco/cuenta/{fecha_ini}/{fecha_fin} (GET):
//	@GetMapping("banco/cuenta/{fecha_ini}/{fecha_fin}")
//	public ArrayList<CuentaBancaria> getCuentasFechaController(@PathVariable String fecha_apertura_1, @PathVariable String fecha_apertura_2) {
//		return cuentaModelo.getCuentasFecha(fecha_apertura_1, fecha_apertura_2);
//	}
//
//	// Insertar nuevas cuentas bancarias: /banco/cuenta/new (PUT):
//	@PutMapping("banco/cuenta/new")
//	public boolean insertNewCuentaController(@RequestParam String numero_de_cuentaIns, @RequestParam ArrayList<String> titularesIns, @RequestParam double saldoIns) {
//		return cuentaModelo.insertNewCuenta(numero_de_cuentaIns, titularesIns, saldoIns);
//	}
//
//	// Actualizar cuentas bancarias por número de cuenta: /banco/cuenta/update
//	// (PUT):
//	@PutMapping("banco/cuenta/update")
//	public boolean updateCuentaController(@RequestParam String numero_de_cuentaIns, @RequestParam ArrayList<String> titularesIns, @RequestParam String fecha_aperturaIns,
//			@RequestParam boolean borradoIns) {
//		return cuentaModelo.updateCuenta(numero_de_cuentaIns, titularesIns, fecha_aperturaIns, borradoIns) ;
//	}
//	
//	// Borrar cuentas bancarias por numero de cuenta: /banco/cuenta/{nro_cuenta}
//	// (DELETE) (soft deletion):
//	@DeleteMapping("banco/cuenta/{numeroDeCuenta}")
//	public boolean deleteCuentaController(@RequestParam String numero_de_cuentaIns) {
//		return cuentaModelo.deleteCuenta(numero_de_cuentaIns);
//	}
//
////         Insertar dinero por numero de cuenta: /banco/cuenta/ingresar/ (PUT con datos {nro_cuenta} e {ingreso})
////         Devuelve un string con el saldo ingresado y el saldo restante.
////         Debe actualizar el saldo.
////         Si el dinero a ingresar es negativo, no hace nada.
//	@PutMapping("banco/cuenta/ingresar")
//	public String insertIngresarCuentaController(@RequestParam String numero_de_cuentaIns, @RequestParam double saldo_entrante) {
//		return numero_de_cuentaIns;
//	}
//
////         Retirar dinero por numero de cuenta: /banco/cuentas/retirar/ (PUT con datos {nro_cuenta} y {retiro})
////         Devuelve un string con el saldo retirado y el saldo restante.
////         Debe actualizar el saldo.
////         Si el dinero a retirar es negativo no hace nada.
////         Si el saldo de la cuenta se queda a 0, solo debe retirar el saldo restante.
//	@PutMapping("banco/cuenta/retirar")
//	public String withdrawDineroCuentaController(@RequestParam String numero_de_cuentaIns, @RequestParam double saldo_saliente) {
//
//		return cuentaModelo.withdrawDineroCuenta(numero_de_cuentaIns, saldo_saliente) ;
//	}
}
//
