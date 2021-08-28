package ar.com.ada.api.aladas;



import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.entities.Usuario;
import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.entities.Vuelo.EstadoVueloEnum;
import ar.com.ada.api.aladas.security.Crypto;
import ar.com.ada.api.aladas.services.AeropuertoService;
import ar.com.ada.api.aladas.services.VueloService;
import ar.com.ada.api.aladas.services.VueloService.ValidacionVueloDataEnum;

@SpringBootTest
class AladasApplicationTests {

	@Autowired
	VueloService vueloService;

	@Autowired
	AeropuertoService aeropuertoService;

	@Test
	void contextLoads() {
	}

	@Test
	void vueloTestPrecioNegativo() {

		Vuelo vueloConPrecioNegativo = new Vuelo();
		vueloConPrecioNegativo.setPrecio(new BigDecimal(-100));

		// assert: afirmar
		// afiermar que sea verdadero: assertFalse
		assertFalse(vueloService.validarPrecio(vueloConPrecioNegativo));
	}

	@Test
	void vueloTestPrecioOK() {

		Vuelo vueloConPrecioOK = new Vuelo();
		vueloConPrecioOK.setPrecio(new BigDecimal(100));

		// assert: afirmar
		// afiermar que sea verdadero: assertTrue
		assertTrue(vueloService.validarPrecio(vueloConPrecioOK));
	}

	@Test
	void aeropuertoValidarCodigoIATAOK(){
		//el código no debe llevar número y sólo 3 letras, así que habría que limitarlo a eso
		
		String codigoIATAOK1 = "EZE";
		String codigoIATAOK2 = "AEP";
		String codigoIATAOK3 = "NQN";

		String codigoIATAOK4 = " QN";

		/*// en este caso, afirmo que espero que el length del codigoIATAOK1 sea 3
		assertEquals(3, codigoIATAOK1.length());

		//en este caso, afirmo que espero que el resultado de la condicion
		//sea verdadero( en este caso, length)
		assertTrue(codigoIATAOK2.length() == 3);*/

		Aeropuerto aeropuerto1 = new Aeropuerto();
		aeropuerto1.setCodigoIATA(codigoIATAOK1);

		Aeropuerto aeropuerto2 = new Aeropuerto();
		aeropuerto2.setCodigoIATA(codigoIATAOK2);

		Aeropuerto aeropuerto3 = new Aeropuerto();
		aeropuerto3.setCodigoIATA(codigoIATAOK3);

		Aeropuerto aeropuerto4 = new Aeropuerto();
		aeropuerto4.setCodigoIATA(codigoIATAOK4);

		assertTrue(aeropuertoService.validarCodigoIATA(aeropuerto1));
		assertTrue(aeropuertoService.validarCodigoIATA(aeropuerto2));
		assertTrue(aeropuertoService.validarCodigoIATA(aeropuerto3));

		assertFalse(aeropuertoService.validarCodigoIATA(aeropuerto4));

	}

	@Test
	void aeropuertoTestBuscadorIATANoOK(){
		// el código no debe llevar número y sólo 3 letras, así que habría que limitarlo a eso
	}

	@Test
	void vueloVerificarValidacionAeropuertoOrigenDestino(){
		// en este validar todas las posibilidades de si el aeropuerto
		// origen es igual al de destino o todo lo que se les ocura
	}

	@Test
	void vueloChequearQueLosPendientesNoTenganVuelosViejos(){
		// se valida que cuando hagamos un metodo que traiga los vuelos actuales para
		// hacer reservas, no haya ningun vuelo en el pasado.
	}

	@Test
	void vueloVerificarCapacidadMinima(){

	}

	@Test
	void vueloVerificarCapacidadMaxima(){
		
	}

	@Test
	void aeropuertoTestBuscadorIATA(){

	}

	@Test
	void vueloValidarVueloMismoDestino(){
		Vuelo vuelo = new Vuelo();
		vuelo.setPrecio(new BigDecimal(1000));
		vuelo.setEstadoVueloId(EstadoVueloEnum.GENERADO);
		vuelo.setAeropuertoOrigen(166);
		vuelo.setAeropuertoDestino(166);

		assertEquals(ValidacionVueloDataEnum.ERROR_AEROPUERTOS_IGUALES, vueloService.validar(vuelo));

	}

	@Test
	void testearEncriptacion(){

		String contraseñaImaginaria = "pitufosasesinos";
		String contraseñaImaginariaEncriptada = Crypto.encrypt(contraseñaImaginaria, "palabra");

		String contraseñaImaginariaEncriptadaDesencriptada = Crypto.decrypt(contraseñaImaginariaEncriptada, "palabra");

		//assertTrue(contraseñaImaginariaEncriptadaDesencriptada.equals(contraseñaImaginaria));
		assertEquals(contraseñaImaginariaEncriptadaDesencriptada, contraseñaImaginaria);
	}

	@Test
	void testearContraseña(){
		Usuario usuario = new Usuario();

		usuario.setUsername("Diana@gmail.com");
		usuario.setPassword("qp5TPhgUtIf7RDylefkIbw==");
		usuario.setEmail("Diana@gmail.com");

		assertFalse(!usuario.getPassword().equals(Crypto.encrypt("AbcdE23", usuario.getUsername().toLowerCase())));

	}
}
