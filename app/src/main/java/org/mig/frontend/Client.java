package org.mig.frontend;

import com.google.gson.annotations.Expose;

/**
 * Created by Miguel on 15/02/2017.
 */
public class Client {

    @Expose
    public Integer id;
    @Expose
    public String direccion;
    @Expose
    public String email;
    @Expose
    public String nombre;
    @Expose
    public String telefono;
    @Expose
    public Integer idCiudad;
    @Expose
    private Integer id_backend;

    public Client(){

    }

    public Client(int id, String direccion, String email, String nombre, String telefono, int idCiudad) {
        this.id = id;
        this.direccion = direccion;
        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
        this.idCiudad = idCiudad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Integer getId_backend() {
        return id_backend;
    }

    public void setId_backend(Integer id_backend) {
        this.id_backend = id_backend;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", direccion='" + direccion + '\'' +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", idCiudad=" + idCiudad +
                ", id_backend=" + id_backend +
                '}';
    }
}
