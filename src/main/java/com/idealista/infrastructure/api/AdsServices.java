package com.idealista.infrastructure.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.idealista.infrastructure.calculator.ScoreCalculator;
import com.idealista.infrastructure.utils.UtilsQA;
import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.InMemoryPersistence;

@Service
public class AdsServices implements AdsServicesInt {
	
	/**
	 * Servicio para calcular y agregar score y irrilevance date.
	 * @param memory 
	 * 
	 *
	 */
	@Override
	public void ScoreAds(InMemoryPersistence memory) {

		try {
			List<AdVO> adsNew = memory.getAdsList().stream()
					.map(v -> ScoreCalculator.setScoreTotal(v, memory.getPicturesList())).collect(Collectors.toList());

			memory.setAds(adsNew);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * servicio que nos devuelve una lista ordenada de Ads para el reparto de qualidad con score y irrilevance Date.
	 * @param memory 
	 * @return List<QualityAd>
	 *
	 */
	@Override
	public List<PublicAd> publicAds(InMemoryPersistence memory) {

		try {
			List<PublicAd> publicAds = memory.getAdsList().stream().filter(x -> x.getScore() != null && x.getScore() > 40)
					.sorted((s1, s2) -> s1.getScore().compareTo(s2.getScore()))
					.map(x -> UtilsQA.setPublicAds(x, memory.getPicturesList())).collect(Collectors.toList());

			return publicAds;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<PublicAd>();
		}

	}

	/**
	 * servicio que nos devuelve una lista ordenada de Ads para el reparto de qualidad con score y irrilevance Date.
	 * @param memory 
	 * @return List<QualityAd>
	 *
	 */
	@Override
	public List<QualityAd> qualityAd(InMemoryPersistence memory) {

		try {
			List<QualityAd> qualityAds = memory.getAdsList().stream().filter(x -> x.getScore() != null)
					.sorted((s1, s2) -> s1.getScore().compareTo(s2.getScore()))
					.map(x -> UtilsQA.setQalityAds(x, memory.getPicturesList())).collect(Collectors.toList());

			return qualityAds;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<QualityAd>();
		}

	}

}
