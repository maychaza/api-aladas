package ar.com.ada.api.aladas.entities;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "aeropuerto")
public class Aeropuerto {

    @Id
    @Column(name = "aeropuerto_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aeropuertoId;

    @Column(name = "nombre_aeropuerto")
    private String nombre;

    @Column(name = "codigo_iata")
    private String codigoIATA;

    
    public Integer getAeropuertoId() {
        return aeropuertoId;
    }

    public void setAeropuertoId(Integer aeropuertoId) {
        this.aeropuertoId = aeropuertoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoIATA() {
        return codigoIATA;
    }

    public void setCodigoIATA(String codigoIATA) {
        this.codigoIATA = codigoIATA;
    }

    
    
}
