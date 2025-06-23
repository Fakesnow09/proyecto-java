package com.miproyecto.Transporte;

import java.sql.Date;

public class Transporte {
    private int idTransporte;
    private String vehiculo;
    private int idRuta;
    private String estado;
    private Date fechaSalida;
    private Date fechaLlegada;

    // Getters y Setters
    public int getIdTransporte() { return idTransporte; }
    public void setIdTransporte(int idTransporte) { this.idTransporte = idTransporte; }

    public String getVehiculo() { return vehiculo; }
    public void setVehiculo(String vehiculo) { this.vehiculo = vehiculo; }

    public int getIdRuta() { return idRuta; }
    public void setIdRuta(int idRuta) { this.idRuta = idRuta; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(Date fechaSalida) { this.fechaSalida = fechaSalida; }

    public Date getFechaLlegada() { return fechaLlegada; }
    public void setFechaLlegada(Date fechaLlegada) { this.fechaLlegada = fechaLlegada; }
}