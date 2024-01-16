package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CuentaBancaria {
    private String numeroDeCuenta;
    private List<String> titulares;
    private double saldo;
    private Date fechaApertura;
    private boolean borrada;
//	
//- Modelo --> CuentaBancaria.java
//		- Clase del objeto


	    // Constructor por defecto
	    public CuentaBancaria() {
	        this.titulares = new ArrayList<>();
	        this.fechaApertura = new Date();
	        this.borrada = false;
	    }

	    // Constructor con parámetros
	    public CuentaBancaria(String numeroDeCuenta, List<String> titulares, double saldo, Date fechaApertura, boolean borrada) {
	        this.numeroDeCuenta = numeroDeCuenta;
	        this.titulares = titulares;
	        this.saldo = saldo;
	        this.fechaApertura = fechaApertura;
	        this.borrada = borrada;
	    }

	    // Getters y setters para cada atributo

	    public String getNumeroDeCuenta() {
	        return numeroDeCuenta;
	    }

	    public void setNumeroDeCuenta(String numeroDeCuenta) {
	        this.numeroDeCuenta = numeroDeCuenta;
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
	        return fechaApertura;
	    }

	    public void setFechaApertura(Date fechaApertura) {
	        this.fechaApertura = fechaApertura;
	    }

	    public boolean isBorrada() {
	        return borrada;
	    }

	    public void setBorrada(boolean borrada) {
	        this.borrada = borrada;
	    }
	}

	

