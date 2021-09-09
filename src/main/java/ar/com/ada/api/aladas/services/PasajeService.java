package ar.com.ada.api.aladas.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Pasaje;
import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.entities.Reserva.EstadoReservaEnum;
import ar.com.ada.api.aladas.repos.PasajeRepository;
import ar.com.ada.api.aladas.repos.ReservaRepository;
import ar.com.ada.api.aladas.repos.VueloRepository;

@Service
public class PasajeService {

    @Autowired
    PasajeRepository repo;

    @Autowired
    ReservaService reservaService;

    @Autowired
    VueloService vueloService;

    public Pasaje emitirPasaje(Integer reservaId) {

        Pasaje pasaje = new Pasaje();
        // pasaje.setPasajeId(reservaId);
        pasaje.setFechaEmision(new Date());

        Reserva reserva = reservaService.buscarPorId(reservaId);
        reserva.setEstadoReservaId(EstadoReservaEnum.EMITIDA);
        reserva.asociarPasaje(pasaje);
        Integer nuevaCapacidad = reserva.getVuelo().getCapacidad() - 1; // crear variable para evitar choclo de codigo
        reserva.getVuelo().setCapacidad(nuevaCapacidad);

        /* problema concurrencia
        "update vuelo set capacidad = where vueloid = 99 and capacidad =30"

        "update vuelo set capacidad = where vueloid = 99 and capacidad =30"
        */
        
        vueloService.actualizar(reserva.getVuelo()); // el vuelo tiene el cambio de capacidad y hay que actualizar los nuevos datos
                                                     
        return pasaje;
    }

}
