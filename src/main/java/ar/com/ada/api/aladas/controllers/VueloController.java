package ar.com.ada.api.aladas.controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.entities.Vuelo.EstadoVueloEnum;
import ar.com.ada.api.aladas.models.request.EstadoVueloRequest;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.AeropuertoService;
import ar.com.ada.api.aladas.services.VueloService;
import ar.com.ada.api.aladas.services.VueloService.ValidacionVueloDataEnum;

@RestController
public class VueloController {

    @Autowired
    VueloService service;

    @Autowired
    AeropuertoService aeroService;

    /*
     * otra forma de inyectar: version "pro"
     * 
     * private VueloService service;
     * 
     * public VueloController(VueloService service){ this.service = service; }
     */

    @PostMapping("/api/vuelos")
    public ResponseEntity<GenericResponse> postCrearVuelo(@RequestBody Vuelo vuelo) {

        GenericResponse respuesta = new GenericResponse();

        ValidacionVueloDataEnum resultadoValidacion = service.validar(vuelo);
        if ( resultadoValidacion == ValidacionVueloDataEnum.OK){
            service.crear(vuelo);

            respuesta.isOk = true;
            respuesta.id = vuelo.getVueloId();
            respuesta.message = "El vuelo fue generado con éxito";

            return ResponseEntity.ok(respuesta);
        }
        else{
            respuesta.isOk = false;
            respuesta.message = "Error(" + resultadoValidacion.toString() + ")";

            return ResponseEntity.badRequest().body(respuesta);
        }
        
    }

    /*/ version 2, sin models
    @PostMapping("/api/v2/vuelos")
    public ResponseEntity<GenericResponse> postCrearVueloV2(@RequestBody Vuelo vuelo) {

        GenericResponse respuesta = new GenericResponse();

        Aeropuerto ao= aeroService.buscarPorId();
        
        Vuelo vueloCreado = service.crear(vuelo.getFecha(), vuelo.getCapacidad(), vuelo.getAeropuertoOrigen(), vuelo.getAeropuertoDestino(), vuelo.getPrecio(), vuelo.getCodigoMoneda());

        respuesta.isOk = true;
        respuesta.id = vueloCreado.getVueloId();
        respuesta.message = "El vuelo fue generado con éxito";

        return ResponseEntity.ok(respuesta);
    }*/

    @PutMapping("/api/vuelos/{id}/estados")
    public ResponseEntity<GenericResponse> putActualizarEstadoVuelo(@PathVariable Integer id, @RequestBody EstadoVueloRequest estadoVuelo){

        GenericResponse respuesta = new GenericResponse();
        respuesta.isOk = true;
        respuesta.message = "Actualizado";

        // Pasos:
       // 1. buscar vuelo por id y lo asigno a 1 variable(vuelo)
        Vuelo vuelo = service.buscarPorId(id);
       // 2. settear wl nuevo estado, q vino en estadoVuelo al vuelo.
       vuelo.setEstadoVueloId(estadoVuelo.estado);
       // 3. grabar el vuelo en la BD
       service.actualizar(vuelo);
       // 4. que vuelve el status final
        return ResponseEntity.ok(respuesta);

    }

    @GetMapping("/api/vuelos/abiertos")
    public List<Vuelo> getVuelosAbiertos(){
        return service.traerVuelosAbiertos();
    }
}
