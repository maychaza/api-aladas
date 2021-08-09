package ar.com.ada.api.aladas.services;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.repos.VueloRepository;

@Service
public class VueloService {
    
    @Autowired
    VueloRepository repo;

    @Autowired
    AeropuertoService aeropService;


    public void crear (Vuelo vuelo){
        repo.save(vuelo);
    }

    public Vuelo crear (Date fecha, Integer capacidad, String aeropuertoOrigenIATA, String aeropuertoDestinoIATA, BigDecimal precio, String codigoMoneda){

        Vuelo vuelo = new Vuelo();
        vuelo.setFecha(fecha);
        vuelo.setCapacidad(capacidad);

        Aeropuerto aeropuertoOrigen = aeropService.buscarPorCodigo(aeropuertoOrigenIATA);
        
        Aeropuerto aeropuertoDestino = aeropService.buscarPorCodigo(aeropuertoDestinoIATA);
        
        vuelo.setAeropuertoOrigen(aeropuertoOrigen.getAeropuertoId());
        vuelo.setAeropuertoDestino(aeropuertoDestino.getAeropuertoId());

        vuelo.setPrecio(precio);
        vuelo.setCodigoMoneda(codigoMoneda);

        return repo.save(vuelo);  // lo guarda derecho en la DB
    }

    public ValidacionVueloDataEnum validar (Vuelo vuelo){

        if (!validarPrecio(vuelo))  // si da false, el precio falló
            return ValidacionVueloDataEnum.ERROR_PRECIO;

        if(!validarAeropuertoOrigenDiffDestino(vuelo))
            return ValidacionVueloDataEnum.ERROR_AEROPUERTOS_IGUALES;

        return ValidacionVueloDataEnum.OK;
    }

    public boolean validarPrecio(Vuelo vuelo){

        if ( vuelo.getPrecio() == null){   // primero false para chequear que no sea nulo
            return false;
        }

        if (vuelo.getPrecio().doubleValue() > 0){
            return true;
        }  
        return false;
    }

    public boolean validarAeropuertoOrigenDiffDestino(Vuelo vuelo){

        /*if (vuelo.getAeropuertoDestino() != vuelo.getAeropuertoOrigen()
            return true;
        else 
            return false;*/
          
        return vuelo.getAeropuertoDestino() != vuelo.getAeropuertoOrigen();  //lo mismo que el if
    }




    public enum ValidacionVueloDataEnum {
        OK, ERROR_PRECIO, ERROR_AEROPUERTO_ORIGEN, ERROR_AEROPUERTO_DESTINO, ERROR_FECHA,
        ERROR_MONEDA, ERROR_CAPACIDAD_MINIMA, ERROR_CAPACIDAD_MAXIMA, ERROR_AEROPUERTOS_IGUALES, ERROR_GENERAL
    }
    
}
