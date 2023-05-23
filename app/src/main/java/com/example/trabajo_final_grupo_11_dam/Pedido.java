package com.example.trabajo_final_grupo_11_dam;

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

    /**
     * Constructor de la clase Pedido.
     *
     * @param id_pedido          El ID del pedido.
     * @param direccionRestaurante La dirección del restaurante.
     * @param direccionCliente    La dirección del cliente.
     * @param precioTotal         El precio total del pedido.
     * @param restauranteId       El ID del restaurante asociado al pedido.
     * @param cliente_Username    El nombre de usuario del cliente que realizó el pedido.
     */
    public Pedido(int id_pedido, String direccionRestaurante, String direccionCliente, int precioTotal, int restauranteId, String cliente_Username) {
        this.id_pedido = id_pedido;
        this.Direccion_Restaurante = direccionRestaurante;
        this.Direccion_Cliente = direccionCliente;
        this.Precio_Total = precioTotal;
        this.RestauranteID = restauranteId;
        this.Cliente_Username = cliente_Username;
    }

    /**
     * Obtiene el ID del pedido.
     *
     * @return El ID del pedido.
     */
    public int getid_pedido() {
        return id_pedido;
    }

    /**
     * Obtiene la dirección del restaurante.
     *
     * @return La dirección del restaurante.
     */
    public String getDireccionRestaurante() {
        return Direccion_Restaurante;
    }

    /**
     * Obtiene la dirección del cliente.
     *
     * @return La dirección del cliente.
     */
    public String getDireccionCliente() {
        return Direccion_Cliente;
    }

    /**
     * Obtiene el precio total del pedido.
     *
     * @return El precio total del pedido.
     */
    public int getPrecioTotal() {
        return Precio_Total;
    }

    /**
     * Obtiene el ID del restaurante asociado al pedido.
     *
     * @return El ID del restaurante asociado al pedido.
     */
    public int getRestauranteId() {
        return RestauranteID;
    }

    /**
     * Obtiene el nombre de usuario del cliente que realizó el pedido.
     *
     * @return El nombre de usuario del cliente.
     */
    public String getCliente_Username() {
        return Cliente_Username;
    }
}

