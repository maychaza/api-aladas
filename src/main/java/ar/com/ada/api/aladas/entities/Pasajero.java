package ar.com.ada.api.aladas.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "pasajero")
public class Pasajero extends Persona {

    @Id
    @Column(name= "pasajero_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pasajeroID;

    @OneToMany(mappedBy = "pasajero", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas = new ArrayList<>();


    public Integer getPasajeroID() {
        return pasajeroID;
    }

    public void setPasajeroID(Integer pasajeroID) {
        this.pasajeroID = pasajeroID;
    }

    public void agregarReserva(Reserva reserva){   // relacion bidireccional
        this.reservas.add(reserva);
        reserva.setPasajero(this);
    }

}
