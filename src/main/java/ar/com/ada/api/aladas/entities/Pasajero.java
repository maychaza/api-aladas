package ar.com.ada.api.aladas.entities;

import javax.persistence.*;


@Entity
@Table(name = "pasajero")
public class Pasajero extends Persona {

    @Id
    @Column(name= "pasajero_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pasajeroID;

    public Integer getPasajeroID() {
        return pasajeroID;
    }

    public void setPasajeroID(Integer pasajeroID) {
        this.pasajeroID = pasajeroID;
    }

}
