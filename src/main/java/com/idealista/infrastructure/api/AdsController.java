package com.idealista.infrastructure.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idealista.infrastructure.persistence.InMemoryPersistence;

/**
 * @author Nicola Gernone
 *
 */
@RestController
@RequestMapping("/ads")
public class AdsController {
	

	@Autowired
	AdsServices service;
	
	/**
	 * Llamada de tipo POST al servicio que nos devuelve una lista ordenada de Ads para el reparto de qualidad con score y irrilevance Date.
	 * @param memory 
	 * produce y cosuma un JSON desde el repository
	 * @return ResponseEntity<List<QualityAd>>
	 *
	 */
	@PostMapping(path ="/qualityAd", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<QualityAd>> qualityListing(@RequestBody InMemoryPersistence memory) {
		try {
			List<QualityAd> qaAds = new AdsServices().qualityAd(memory);
			
			return ResponseEntity.ok().body(qaAds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}
    }
	
	/**
	 * Llamada de tipo POST al servicio que nos devuelve una lista ordenada de Ads para el usuario.
	 * Devuelve solo anuncios rilevantes que tienen un score mayor de 40 puntos.
	 * @param memory 
	 * produce y consuma un JSON dessde el repository
	 * @return ResponseEntity<List<PublicAd>>
	 *
	 */
    @PostMapping(path ="/publicAd", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<PublicAd>> publicListing(@RequestBody InMemoryPersistence memory) {
    	try {
			List<PublicAd> pAds = new AdsServices().publicAds(memory);
			return ResponseEntity.ok().body(pAds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}
    }
    
    /**
	 * Llamada de tipo GET al servicio que calcula y agrega score y irrilevance Date de cada anuncio.
	 * Si ubiese tenido que ser una modifica puntual se habria usado un metodo PUT o PATCH.
	 * @param memory 
	 * consuma un JSON desde el repository
	 * @return ResponseEntity<Void>
	 *
	 */
    @GetMapping(path = "/score", consumes = "application/json")
	public ResponseEntity<Void> calculateScore(@RequestBody InMemoryPersistence memory) {
    	try {
			new AdsServices().ScoreAds(memory);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}
    }
}
