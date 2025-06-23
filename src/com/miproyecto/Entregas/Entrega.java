package com.miproyecto.Entregas;

import java.sql.Date;

public class Entrega {
    private int idEntrega;
    private int idPedido;
    private int idTransporte;
    private Date fechaSalida;
    private Date fechaLlegada;
    private String estado;

    public int getIdEntrega() { return idEntrega; }
    public void setIdEntrega(int idEntrega) { this.idEntrega = idEntrega; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public int getIdTransporte() { return idTransporte; }
    public void setIdTransporte(int idTransporte) { this.idTransporte = idTransporte; }

    public Date getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(Date fechaSalida) { this.fechaSalida = fechaSalida; }

    public Date getFechaLlegada() { return fechaLlegada; }
    public void setFechaLlegada(Date fechaLlegada) { this.fechaLlegada = fechaLlegada; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}