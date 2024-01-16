package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CuentaBancaria {
	private String numero_de_cuenta;
	private List<String> titulares;
	private double saldo;
	private Date fecha_apertura;
	private boolean borrada;

	// Constructor por defecto
	public CuentaBancaria() {
		this.titulares = new ArrayList<>();
		this.fecha_apertura = new Date();
		this.borrada = false;
	}

	// Constructor con parámetros
	public CuentaBancaria(String numeroDeCuenta, List<String> titulares, double saldo, Date fechaApertura,
			boolean borrada) {
		this.numero_de_cuenta = numeroDeCuenta;
		this.titulares = titulares;
		this.saldo = saldo;
		this.fecha_apertura = fechaApertura;
		this.borrada = borrada;
	}

	// Getters y setters para cada atributo

	public String getNumeroDeCuenta() {
		return numero_de_cuenta;
	}

	public void setNumeroDeCuenta(String numeroDeCuenta) {
		this.numero_de_cuenta = numeroDeCuenta;
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
		return fecha_apertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fecha_apertura = fechaApertura;
	}

	public boolean isBorrada() {
		return borrada;
	}

	public void setBorrada(boolean borrada) {
		this.borrada = borrada;
	}
}
