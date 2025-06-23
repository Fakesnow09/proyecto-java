package com.miproyecto.Carga;

public class Carga {
    private int idCarga;
    private int idTransporte;
    private String descripcion;
    private double peso;
    private String estado;

    // Getters y Setters
    public int getIdCarga() { return idCarga; }
    public void setIdCarga(int idCarga) { this.idCarga = idCarga; }

    public int getIdTransporte() { return idTransporte; }
    public void setIdTransporte(int idTransporte) { this.idTransporte = idTransporte; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
