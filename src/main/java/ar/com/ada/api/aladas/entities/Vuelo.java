package ar.com.ada.api.aladas.entities;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "vuelo")
public class Vuelo {

    @Id
    @Column(name = "vuelo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vueloId;

    private Date fecha;

    //enumerado
    @Column(name = "estado_vuelo_id")
    private Integer estadoVueloId;

    private Integer capacidad;

    @Column(name = "aeropuerto_origen")
    private Integer aeropuertoOrigen;

    @Column(name = "aeropuerto_destino")
    private Integer aeropuertoDestino;

    private BigDecimal precio;

    @Column(name = "codigo_moneda")
    private String codigoMoneda;

    @OneToMany(mappedBy = "vuelo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas = new ArrayList<>();

    public void agregarReserva(Reserva reserva){
        this.reservas.add(reserva);
        reserva.setVuelo(this);
    }
}
