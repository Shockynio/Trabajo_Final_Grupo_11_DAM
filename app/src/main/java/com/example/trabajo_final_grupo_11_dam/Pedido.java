package com.example.trabajo_final_grupo_11_dam;

public class Pedido {
    private int id_pedido;
    private String Direccion_Restaurante;
    private String Direccion_Cliente;
    private int Precio_Total;
    private int RestauranteID;
    private String Cliente_Username;

    public Pedido(int id_pedido, String direccionRestaurante, String direccionCliente, int precioTotal, int restauranteId, String cliente_Username) {
        this.id_pedido = id_pedido;
        this.Direccion_Restaurante = direccionRestaurante;
        this.Direccion_Cliente = direccionCliente;
        this.Precio_Total = precioTotal;
        this.RestauranteID = restauranteId;
        this.Cliente_Username = cliente_Username;
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
}

