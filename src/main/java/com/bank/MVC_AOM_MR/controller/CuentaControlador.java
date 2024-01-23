package com.bank.MVC_AOM_MR.controller;

import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping("/banco/cuenta")
public class CuentaControlador {
	CuentaModelo cuentaModelo = new CuentaModelo();

	public CuentaControlador() {

	}

	@GetMapping("/hi")
	public String hi() {
		String hiStr = "Hello, world!";
		return hiStr;
	}

	// Mostrar todas las cuentas bancarias: /banco/cuenta (GET) (No debe mostrar las
	// cuentas borradas):
	@GetMapping("/")
	public ArrayList<CuentaBancaria> getCuentasController() {
		return cuentaModelo.getAllAccounts();
	}

	// Mostrar cuentas bancarias por numero de cuenta: /banco/cuenta/{nro_cuenta}
	// (GET):
	@GetMapping("/{nro_cuenta}")
	public CuentaBancaria getCuentaNumberController(@PathVariable String numberAccoutnIns) {

		return cuentaModelo.getAccountPerNumber(numberAccoutnIns);
	}

	// Mostrar cuentas bancarias por rango de fecha de apertura:
	// /banco/cuenta/{fecha_ini}/{fecha_fin} (GET):
	@GetMapping("/{fecha_ini}/{fecha_fin}")
	public ArrayList<CuentaBancaria> getCuentasFechaController(
			@PathVariable Date startingDateIns_1,
			@PathVariable Date startingDateIns_2) {

		return cuentaModelo.getAccountPerDate(startingDateIns_1, startingDateIns_2);
	}

	// Insertar nuevas cuentas bancarias: /banco/cuenta/new (PUT):
	@PutMapping("/new")
	public boolean insertNewCuentaController(
			@RequestParam String numberAccountIns,
			@RequestParam ArrayList<String> ownersIns, 
			@RequestParam double balanceIns
//			,@RequestParam Date startingDateIns
			) {
		boolean creationStatus = false;
		CuentaBancaria recoveredAccount = cuentaModelo.getAccountPerNumber(numberAccountIns);
		if (recoveredAccount != null) {
			recoveredAccount.setAccountNumber(numberAccountIns);
			recoveredAccount.setOwners(ownersIns);
			recoveredAccount.setBalance(balanceIns);
			recoveredAccount.setStartingDate(new Date());
			recoveredAccount.setDeleted(false);
			cuentaModelo.insertNewAccount(recoveredAccount);
		}

		return cuentaModelo.insertNewAccount(recoveredAccount);
	}

	// Actualizar cuentas bancarias por numero de cuenta: /banco/cuenta/update
	// (PUT):
	@PutMapping("/update")
	public boolean updateCuentaController(@RequestParam String numberAccountIns,
			@RequestParam ArrayList<String> ownersIns,
//			@RequestParam double balanceIns,
			@RequestParam Date startingDateIns
//			, @RequestParam boolean deletedEdit
	) {

		CuentaBancaria recoveredAccount = cuentaModelo.getAccountPerNumber(numberAccountIns);

		if (recoveredAccount != null) {
				recoveredAccount.setStartingDate(startingDateIns);
				recoveredAccount.setOwners(ownersIns);
		} else {

		}

		return cuentaModelo.updateAccount(recoveredAccount);
	}

	// Borrar cuentas bancarias por numero de cuenta: /banco/cuenta/{nro_cuenta}
	// APARENTEMENTE HECHO
	// (DELETE) (soft deletion):
	@DeleteMapping("/delete/{numeroDeCuenta}")
	public String deleteCuentaController(@RequestParam String numberAccountIns, @RequestParam boolean deletedEdit) {
		String estado = "La cuenta con numero de cuenta " + numberAccountIns + " ";
		CuentaBancaria recoveredAccount = cuentaModelo.getAccountPerNumber(numberAccountIns);

		if (recoveredAccount != null) {
			recoveredAccount.setDeleted(deletedEdit);
		}

		cuentaModelo.updateAccount(recoveredAccount);

		return estado;
	}

//         Insertar dinero por numero de cuenta: /banco/cuenta/ingresar/ (PUT con datos {nro_cuenta} e {ingreso})
//         Devuelve un string con el saldo ingresado y el saldo restante.
//         Debe actualizar el saldo.
//         Si el dinero a ingresar es negativo, no hace nada.
	@PutMapping("/deposit")
	public String insertIngresarCuentaController(@RequestParam String numberAccountIns,
			@RequestParam double balanceIns) {
		String estado = "";
		CuentaBancaria recoveredAccount = cuentaModelo.getAccountPerNumber(numberAccountIns);

		if (balanceIns > 0.00) {

			recoveredAccount.depositMoney(balanceIns);

			estado = "Se ha realizado un ingreso por la cantidad de " + balanceIns + " euros." + "\n"
					+ "El saldo total de la cuenta con numero de cuenta " + numberAccountIns + " es de "
					+ recoveredAccount.getBalance();

			cuentaModelo.updateAccount(recoveredAccount);
		} else {
			estado = "Por favor, inserte una cantidad valida a ingresar.";
		}

		return estado;
	}

//         Retirar dinero por numero de cuenta: /banco/cuentas/retirar/ (PUT con datos {nro_cuenta} y {retiro})
//         Devuelve un string con el saldo retirado y el saldo restante.
//         Debe actualizar el saldo.
//         Si el dinero a retirar es negativo no hace nada.
//         Si el saldo de la cuenta se queda a 0, solo debe retirar el saldo restante.
	@PutMapping("/withdraw")
	public String withdrawDineroCuentaController(@RequestParam String numberAccountIns,
			@RequestParam double balanceExtr) {
		String estado = "";
		CuentaBancaria recoveredAccount = cuentaModelo.getAccountPerNumber(numberAccountIns);
		double currentAccountBalance = recoveredAccount.getBalance();

		if (balanceExtr > 0.00 && currentAccountBalance >= balanceExtr) {

			recoveredAccount.withdrawMoney(balanceExtr);

			estado = "Se ha realizado un retiro por la cantidad de " + balanceExtr + " euros." + "\n"
					+ "El saldo total de la cuenta con numero de cuenta " + numberAccountIns + " es de "
					+ recoveredAccount.getBalance();

			cuentaModelo.updateAccount(recoveredAccount);
		} else if (balanceExtr > 0.00 && balanceExtr > currentAccountBalance) {
			recoveredAccount.setBalance(0);
			estado = "El saldo de la cuenta es de" + recoveredAccount.getBalance() + " euros.";

			cuentaModelo.updateAccount(recoveredAccount);
		} else {
			estado = "Por favor, inserte una cantidad valida para retirar.";
		}

		return estado;
	}
}
//
