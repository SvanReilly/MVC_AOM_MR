package com.bank.MVC_AOM_MR.controller;

import java.text.DateFormat;
//import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.print.attribute.standard.MediaSize.ISO;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.MVC_AOM_MR.model.BankAccount;
import com.bank.MVC_AOM_MR.model.ModelAccount;

@RestController
@RequestMapping("/bank/account")
public class ControllerAccount {
	ModelAccount modelAccountController = new ModelAccount();
	
	SimpleDateFormat dateFormat;
	Date parsedDate1, parsedDate2;
	SimpleDateFormat outputDateFormat;
	String finalDateFormat;
	Date startingDateOldParsed, startingDateRecentParsed;
	

	public ControllerAccount() {

	}

	@GetMapping("/hi")
	public String hi() {
		String hiStr = "Hello, world!";
		return hiStr;
	}

	// Mostrar todas las cuentas bancarias: /banco/cuenta (GET) (No debe mostrar las
	// cuentas borradas):
	// 100% FUNCIONAL
	@GetMapping("/")
	public ArrayList<BankAccount> getAllAccountsController() {
		return modelAccountController.getAllAccounts();

	}

	// Mostrar cuentas bancarias por numero de cuenta: /banco/cuenta/{nro_cuenta}
	// (GET):
	// 100% FUNCIONAL
	@GetMapping("/{accountNumberIns}")
	public BankAccount getAccountPerNumberController(@PathVariable String accountNumberIns) {
		BankAccount recoveredAccount = modelAccountController.getAccountPerNumber(accountNumberIns);
//		System.out.println(recoveredAccount);
		return recoveredAccount;
		
	}

	// Mostrar cuentas bancarias por rango de fecha de apertura:
	// /banco/cuenta/{fecha_ini}/{fecha_fin} (GET):
	// TEST PENDING ...
	@GetMapping("/{startingDateOld}/{startingDateRecent}")
	public ArrayList<BankAccount> getAccountPerDateController(@PathVariable String startingDateOld, @PathVariable String startingDateRecent) {
		try {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			parsedDate1 = dateFormat.parse(startingDateOld);
			parsedDate2 = dateFormat.parse(startingDateRecent);

			outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

			finalDateFormat = outputDateFormat.format(parsedDate1);
			startingDateOldParsed = (Date) outputDateFormat.parse(finalDateFormat);

			finalDateFormat = outputDateFormat.format(parsedDate2);
			startingDateRecentParsed = (Date) outputDateFormat.parse(finalDateFormat);
			

		} catch (Exception e) {
			
//			e.printStackTrace();
		}

		return modelAccountController.getAccountPerDate(startingDateOldParsed, startingDateRecentParsed);
	}

	// Insertar nuevas cuentas bancarias: /banco/cuenta/new (PUT):
	// 100% FUNCIONAL
	@PutMapping("/new")
	public boolean insertNewAccountController(
			@RequestParam String accountNumberIns,
			@RequestParam ArrayList<String> ownersIns, 
			@RequestParam double startingBalance
//			,@RequestParam Date startingDateIns
	) {
		boolean boolean_status = false;
		BankAccount recoveredAccount = modelAccountController.getAccountPerNumber(accountNumberIns);
//		System.out.println(recoveredAccount.getAccountNumber());
		
		if (recoveredAccount.getAccountNumber().equals("No existe")) {
			recoveredAccount = new BankAccount(
					accountNumberIns, 
					ownersIns, 
					startingBalance, 
					new Date(), 
					false);
			modelAccountController.insertNewAccount(recoveredAccount);
			boolean_status = true;
		} else {
			boolean_status = false;
		}
		return boolean_status;
	}

	// Actualizar cuentas bancarias por numero de cuenta: /banco/cuenta/update
	// (PUT):
	// TEST PENDING...
	@PutMapping("/update")
	public boolean updateAccountController(@RequestParam String accountNumberIns,
			@RequestParam ArrayList<String> ownersIns,
//			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam Date startingDateIns, 
			@RequestParam boolean deletedEdit
	) {
		boolean boolean_status = false;
		BankAccount recoveredAccount = modelAccountController.getAccountPerNumber(accountNumberIns);
//		System.out.println(recoveredAccount.toString());
		if (recoveredAccount != null) {
//			recoveredAccount.setStartingDate(startingDateIns);
			recoveredAccount.setOwners(ownersIns);
			recoveredAccount.setDeleted(deletedEdit);
			modelAccountController.updateAccount(recoveredAccount);
			boolean_status = true;
		} 

		return boolean_status;
	}

//	public static void main(String[] args) {
//		ControllerAccount controllerAccount = new ControllerAccount();
//		ArrayList<String> ownersMain= new ArrayList<String>();
//		ownersMain.add("PaquitaMermela");
//		
//		controllerAccount.updateAccountController("maikyCuadrosEsPuto", ownersMain, null, false);
//		
//		System.out.println(controllerAccount
//				.getAccountPerNumberController("maikyCuadrosEsPuto")
//				.getStartingDate().toString());
//	} 
	// Borrar cuentas bancarias por numero de cuenta: /banco/cuenta/{nro_cuenta}
	// 100% FUNCIONAL
	// (DELETE) (soft deletion):
	@PutMapping("/delete/{accountNumberIns}") 
	public String deleteAccountController(@PathVariable String accountNumberIns) {
		String estado = "La cuenta con numero de cuenta " + accountNumberIns + " ";
		BankAccount recoveredAccount = modelAccountController.getAccountPerNumber(accountNumberIns);
//		System.out.println(recoveredAccount);
		
		if (recoveredAccount.getAccountNumber().equals("No existe")) {
			estado += "no existe. Prueba con otro numero de cuenta.";
			
		} else if (recoveredAccount != null && recoveredAccount.isDeleted() == false) {
			recoveredAccount.setDeleted(true);
			estado += "ha sido eliminada satisfactoriamente.";
			
			modelAccountController.updateAccount(recoveredAccount);
			
		} else if (recoveredAccount != null && recoveredAccount.isDeleted() == true) {
			estado += "ya se encuentra eliminada.";
		} else {
			estado = "NaN";
		}

		return estado;
	}

//         Insertar dinero por numero de cuenta: /banco/cuenta/ingresar/ (PUT con datos {nro_cuenta} e {ingreso})
//         Devuelve un string con el saldo ingresado y el saldo restante.
//         Debe actualizar el saldo.
//         Si el dinero a ingresar es negativo, no hace nada.
	// 100% FUNCIONAL
	@PutMapping("/deposit")
	public String depositAccountController(
			@RequestParam String accountNumberIns,
			@RequestParam double balanceDep) {
		String estado = "";
		BankAccount recoveredAccount = modelAccountController.getAccountPerNumber(accountNumberIns);
		System.out.println(balanceDep);
		
		if (!recoveredAccount.getAccountNumber().equals("No existe")) {

			recoveredAccount.depositMoney(balanceDep);
			estado = "Se ha realizado un ingreso por la cantidad de " 
			+ balanceDep + " euros." 
			+ "\n"
			+ "El saldo total de la cuenta con numero de cuenta " 
			+ accountNumberIns + " es de "
			+ recoveredAccount.getBalance();

			modelAccountController.updateAccount(recoveredAccount);
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
	// 100% FUNCIONAL
	@PutMapping("/withdraw")
	public String withdrawDineroCuentaController(
			@RequestParam String accountNumberIns,
			@RequestParam double balanceExtr) {
		String estado = "";
		BankAccount recoveredAccount = modelAccountController.getAccountPerNumber(accountNumberIns);


		if (!recoveredAccount.getAccountNumber().equals("No existe")) {
			recoveredAccount.withdrawMoney(balanceExtr);

			estado = "Se ha realizado un retiro por la cantidad de " + balanceExtr + " euros." + "\n"
					+ "El saldo actual de la cuenta con numero de cuenta " + accountNumberIns + " es de "
					+ recoveredAccount.getBalance();

			modelAccountController.updateAccount(recoveredAccount);

		} else {

			estado = "Por favor, inserte una cantidad valida para retirar.";
		}

		return estado;
	}
}
//
