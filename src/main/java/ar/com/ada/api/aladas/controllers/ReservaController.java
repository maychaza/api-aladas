package ar.com.ada.api.aladas.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.models.request.InfoReservaNueva;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.ReservaService;


@RestController
public class ReservaController {

    @Autowired
    ReservaService service;

    @PostMapping("api/reservas")
    public ResponseEntity<GenericResponse> generarReserva(@RequestBody InfoReservaNueva infoReserva){
        GenericResponse respuesta = new GenericResponse();

        Integer numeroReserva = service.generarReserva(infoReserva.vueloId);

        respuesta.isOk = true;
        respuesta.id = numeroReserva;
        respuesta.message = "Se crea la reserva con éxito";

        return ResponseEntity.ok(respuesta);
    }
    
    
}
