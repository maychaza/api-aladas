package ar.com.ada.api.aladas.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.aladas.entities.Pasaje;

public interface PasajeRepository extends JpaRepository<Pasaje, Integer> {
    
}
