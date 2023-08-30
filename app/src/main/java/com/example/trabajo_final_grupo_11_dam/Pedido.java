package com.example.trabajo_final_grupo_11_dam;

import java.util.Date;

/**
 * Clase que representa un pedido realizado por un cliente.
 */
public class Pedido {
    private int id_pedido;
    private String Direccion_Restaurante;
    private String Direccion_Cliente;
    private int Precio_Total;
    private int RestauranteID;
    private String Cliente_Username;
    private boolean isTaken;
    private String RepartidorAsignadoEmail;
    private boolean isFinished;
    private Date CreacionPedido;
    private Date TomaPedido;
    private Date FinalizacionPedido;


    public Pedido(int id_pedido, String direccionRestaurante, String direccionCliente, int precioTotal, int restauranteId, String cliente_Username, boolean isTaken, Date CreacionPedido, Date TomaPedido, Date FinalizacionPedido) {
        this.id_pedido = id_pedido;
        this.Direccion_Restaurante = direccionRestaurante;
        this.Direccion_Cliente = direccionCliente;
        this.Precio_Total = precioTotal;
        this.RestauranteID = restauranteId;
        this.Cliente_Username = cliente_Username;
        this.isTaken = isTaken;
        this.CreacionPedido = CreacionPedido;
        this.TomaPedido = TomaPedido;
        this.FinalizacionPedido = FinalizacionPedido;
    }

    public int getid_pedido() {
        return id_pedido;
    }

    public String getDireccionRestaurante() {
        return Direccion_Restaurante;
    }

    public String getDireccionCliente() {
        return Direccion_Cliente;
    }

    public int getPrecioTotal() {
        return Precio_Total;
    }

    public int getRestauranteId() {
        return RestauranteID;
    }

    public String getCliente_Username() {
        return Cliente_Username;
    }

    public boolean getIsTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        this.isTaken = taken;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public String getRepartidorAsignadoEmail() {
        return RepartidorAsignadoEmail;
    }

    public void setRepartidorAsignadoEmail(String repartidorAsignadoEmail) {
        this.RepartidorAsignadoEmail = repartidorAsignadoEmail;
    }

    public Date getCreacionPedido() {
        return CreacionPedido;
    }

    public void setCreacionPedido(Date CreacionPedido) {
        this.CreacionPedido = CreacionPedido;
    }

    public Date getTomaPedido() {
        return TomaPedido;
    }

    public void setTomaPedido(Date TomaPedido) {
        this.TomaPedido = TomaPedido;
    }

    public Date getFinalizacionPedido() {
        return FinalizacionPedido;
    }

    public void setFinalizacionPedido(Date FinalizacionPedido) {
        this.FinalizacionPedido = FinalizacionPedido;
    }
}
