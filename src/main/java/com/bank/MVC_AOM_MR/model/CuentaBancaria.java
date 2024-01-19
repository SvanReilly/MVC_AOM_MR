package com.bank.MVC_AOM_MR.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

public class CuentaBancaria {
	private String numero_de_cuenta;
	private List<String> titulares;
	private double saldo;
	private Date fecha_de_apertura;
	private boolean borrada;

	// Constructor por defecto
	public CuentaBancaria() {
		this.numero_de_cuenta= "";
		this.titulares = new ArrayList<>();
		this.saldo= 0;
		this.fecha_de_apertura = new Date();
		this.borrada = false;
	}

	public CuentaBancaria(Document cuentaDocumento) {
		this.numero_de_cuenta = cuentaDocumento.getString("numero_de_cuenta");
		this.titulares = (ArrayList<String>) cuentaDocumento.get("titulares");
		this.saldo = cuentaDocumento.get("saldo", Number.class).doubleValue();
		this.fecha_de_apertura = (Date) cuentaDocumento.getDate("fecha_de_apertura");
		this.borrada = cuentaDocumento.getBoolean("borrada", false);
	}

	// Constructor con parámetros
	public CuentaBancaria(String numero_de_cuenta, List<String> titulares, double saldo, Date fecha_de_apertura,
			boolean borrada) {
		this.numero_de_cuenta = numero_de_cuenta;
		this.titulares = titulares;
		this.saldo = saldo;
		this.fecha_de_apertura = fecha_de_apertura;
		this.borrada = borrada;
	}

	// Getters y setters para cada atributo

	public String getNumeroDeCuenta() {
		return numero_de_cuenta;
	}

	public void setNumeroDeCuenta(String numero_de_cuenta) {
		this.numero_de_cuenta = numero_de_cuenta;
	}

	public List<String> getTitulares() {
		return titulares;
	}

	public void setTitulares(List<String> titulares) {
		this.titulares = titulares;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Date getFechaApertura() {
		return fecha_de_apertura;
	}

	public void setFechaApertura(Date fecha_de_apertura) {
		this.fecha_de_apertura = fecha_de_apertura;
	}

	public boolean isBorrada() {
		return borrada;
	}

	public void setBorrada(boolean borrada) {
		this.borrada = borrada;
	}
}
