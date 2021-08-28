package ar.com.ada.api.aladas.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.entities.Usuario;
import ar.com.ada.api.aladas.models.request.InfoReservaNueva;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.ReservaService;
import ar.com.ada.api.aladas.services.UsuarioService;


@RestController
public class ReservaController {

    @Autowired
    ReservaService service;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("api/reservas")
    public ResponseEntity<GenericResponse> generarReserva(@RequestBody InfoReservaNueva infoReserva){
        GenericResponse respuesta = new GenericResponse();

        //Obtengo quien esta autenticado del otro lado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //De lo que esta autenticado obtengo el UserName
        String username = authentication.getName();
        
        //Buscar el usuario por username
        Usuario usuario = usuarioService.buscarPorUsername(username);

        // con el usuario, obtengo el pasajero y con ese, obtengo el Id
        Integer numeroReserva = service.generarReserva(infoReserva.vueloId, usuario.getPasajero().getPasajeroId());

        respuesta.isOk = true;
        respuesta.id = numeroReserva;
        respuesta.message = "Se crea la reserva con éxito";

        return ResponseEntity.ok(respuesta);
    }
    
    
}
