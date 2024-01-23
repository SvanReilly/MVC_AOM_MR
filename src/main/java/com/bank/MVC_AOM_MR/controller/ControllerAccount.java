package com.bank.MVC_AOM_MR.controller;

import java.util.ArrayList;
import java.util.Date;

import org.bson.Document;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.MVC_AOM_MR.model.BankAccount;
import com.bank.MVC_AOM_MR.model.ModelAccount;

@RestController
//@RequestMapping("/bank/account")
public class ControllerAccount {
	ModelAccount modelAccountController = new ModelAccount();

	public ControllerAccount() {

	}

	@GetMapping("/hi")
	public String hi() {
		String hiStr = "Hello, world!";
		return hiStr;
	}

	// Mostrar todas las cuentas bancarias: /banco/cuenta (GET) (No debe mostrar las
	// cuentas borradas):
	@GetMapping("/")
	public ArrayList<BankAccount> getAllAccountsController() {
		return modelAccountController.getAllAccounts();

	}

	// Mostrar cuentas bancarias por numero de cuenta: /banco/cuenta/{nro_cuenta}
	// (GET):
	@GetMapping("/bank/account/{accountNumberIns}")
	public BankAccount getAccountPerNumberController(@PathVariable String accountNumberIns) {
		BankAccount recoveredAccount = modelAccountController.getAccountPerNumber(accountNumberIns);
		System.out.println(recoveredAccount);
		return recoveredAccount;
		
	}

	// Mostrar cuentas bancarias por rango de fecha de apertura:
	// /banco/cuenta/{fecha_ini}/{fecha_fin} (GET):
	@GetMapping("/{fecha_ini}/{fecha_fin}")
	public ArrayList<BankAccount> getAccountPerDateController(@PathVariable Date startingDateIns_1,
			@PathVariable Date startingDateIns_2) {

		return modelAccountController.getAccountPerDate(startingDateIns_1, startingDateIns_2);
	}

	// Insertar nuevas cuentas bancarias: /banco/cuenta/new (PUT):
	@PutMapping("/bank/account/new")
	public boolean insertNewAccountController(@RequestParam String accountNumberIns,
			@RequestParam ArrayList<String> ownersIns, @RequestParam double balanceIns
//			,@RequestParam Date startingDateIns
	) {
		boolean boolean_status = false;
		BankAccount recoveredAccount = modelAccountController.getAccountPerNumber(accountNumberIns);
		
		if (recoveredAccount== null) {
			recoveredAccount = new BankAccount(
					accountNumberIns, 
					ownersIns, 
					balanceIns, 
					new Date(), 
					false);

			Document cuentaModeloDocumentoController = new Document()
					.append("account_number", recoveredAccount.getAccountNumber())
					.append("owners", recoveredAccount.getOwners())
					.append("balance", recoveredAccount.getBalance())
					.append("starting_date", new Date())
					.append("deleted", false);

			modelAccountController.insertNewAccount(cuentaModeloDocumentoController);
			boolean_status = true;
		} else {
			boolean_status = false;
		}

		return boolean_status;
	}

	// Actualizar cuentas bancarias por numero de cuenta: /banco/cuenta/update
	// (PUT):
	@PutMapping("/update")
	public boolean updateAccountController(@RequestParam String accountNumberIns,
			@RequestParam ArrayList<String> ownersIns,
//			@RequestParam double balanceIns,
			@RequestParam Date startingDateIns
//			, @RequestParam boolean deletedEdit
	) {
		boolean boolean_status = false;
		BankAccount recoveredAccount = modelAccountController.getAccountPerNumber(accountNumberIns);
		Document cuentaModeloDocumentoController = new Document()
				.append("account_number", recoveredAccount.getAccountNumber())
				.append("owners", recoveredAccount.getOwners())
				.append("balance", recoveredAccount.getBalance())
				.append("starting_date", recoveredAccount.getStartingDate())
				.append("deleted", recoveredAccount.isDeleted());
		
		if (recoveredAccount != null) {
			recoveredAccount.setStartingDate(startingDateIns);
			recoveredAccount.setOwners(ownersIns);
			
			cuentaModeloDocumentoController.replace("starting_date", recoveredAccount.getOwners());
			cuentaModeloDocumentoController.replace("starting_date", recoveredAccount.getStartingDate());
			modelAccountController.updateAccount(cuentaModeloDocumentoController);
			boolean_status = true;
		} 

		return boolean_status;
	}

	// Borrar cuentas bancarias por numero de cuenta: /banco/cuenta/{nro_cuenta}
	// APARENTEMENTE HECHO
	// (DELETE) (soft deletion):
	@DeleteMapping("/delete/{numeroDeCuenta}")
	public String deleteAccountController(@RequestParam String accountNumberIns) {
		String estado = "La cuenta con numero de cuenta " + accountNumberIns + " ";
		BankAccount recoveredAccount = modelAccountController.getAccountPerNumber(accountNumberIns);
		Document cuentaModeloDocumentoController = new Document()
				.append("account_number", recoveredAccount.getAccountNumber())
				.append("owners", recoveredAccount.getOwners())
				.append("balance", recoveredAccount.getBalance())
				.append("starting_date", recoveredAccount.getStartingDate())
				.append("deleted", recoveredAccount.isDeleted());
		
		if (recoveredAccount != null && recoveredAccount.isDeleted() == false) {
			recoveredAccount.setDeleted(true);
			estado += "ha sido eliminada satisfactoriamente.";
			
			cuentaModeloDocumentoController.replace("deleted", recoveredAccount.isDeleted());
			modelAccountController.updateAccount(cuentaModeloDocumentoController);
			
		} else if (recoveredAccount != null && recoveredAccount.isDeleted() == true) {
			estado += "ya se encuentra eliminada.";
		} else {
			estado += "no existe. Prueba con otro numero de cuenta.";
		}

		

		return estado;
	}

//         Insertar dinero por numero de cuenta: /banco/cuenta/ingresar/ (PUT con datos {nro_cuenta} e {ingreso})
//         Devuelve un string con el saldo ingresado y el saldo restante.
//         Debe actualizar el saldo.
//         Si el dinero a ingresar es negativo, no hace nada.
	@PutMapping("/deposit")
	public String depositAccountController(@RequestParam String accountNumberIns,
			@RequestParam double balanceIns) {
		String estado = "";
		BankAccount recoveredAccount = modelAccountController.getAccountPerNumber(accountNumberIns);

		if (balanceIns > 0.00) {

			recoveredAccount.depositMoney(balanceIns);

			estado = "Se ha realizado un ingreso por la cantidad de " + balanceIns + " euros." + "\n"
					+ "El saldo total de la cuenta con numero de cuenta " + accountNumberIns + " es de "
					+ recoveredAccount.getBalance();
			Document cuentaModeloDocumentoController = new Document()
					.append("account_number", recoveredAccount.getAccountNumber())
					.append("owners", recoveredAccount.getOwners())
					.append("balance", recoveredAccount.getBalance())
					.append("starting_date", recoveredAccount.getStartingDate())
					.append("deleted", recoveredAccount.isDeleted());

			modelAccountController.updateAccount(cuentaModeloDocumentoController);
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
	public String withdrawDineroCuentaController(@RequestParam String accountNumberIns,
			@RequestParam double balanceExtr) {
		String estado = "";
		BankAccount recoveredAccount = modelAccountController.getAccountPerNumber(accountNumberIns);
		double currentAccountBalance = recoveredAccount.getBalance();
		Document cuentaModeloDocumentoController = new Document()
				.append("account_number", recoveredAccount.getAccountNumber())
				.append("owners", recoveredAccount.getOwners()).append("balance", recoveredAccount.getBalance())
				.append("starting_date", recoveredAccount.getStartingDate())
				.append("deleted", recoveredAccount.isDeleted());

		if (balanceExtr > 0.00 && currentAccountBalance >= balanceExtr) {

			recoveredAccount.withdrawMoney(balanceExtr);

			estado = "Se ha realizado un retiro por la cantidad de " + balanceExtr + " euros." + "\n"
					+ "El saldo total de la cuenta con numero de cuenta " + accountNumberIns + " es de "
					+ recoveredAccount.getBalance();

			cuentaModeloDocumentoController.replace("balance", recoveredAccount.getBalance());

			modelAccountController.updateAccount(cuentaModeloDocumentoController);

		} else if (balanceExtr > 0.00 && balanceExtr > currentAccountBalance) {

			recoveredAccount.setBalance(0);
			estado = "El saldo de la cuenta es de" + recoveredAccount.getBalance() + " euros.";

			cuentaModeloDocumentoController.replace("balance", recoveredAccount.getBalance());
			modelAccountController.updateAccount(cuentaModeloDocumentoController);
		} else {

			estado = "Por favor, inserte una cantidad valida para retirar.";
		}

		return estado;
	}
}
//
