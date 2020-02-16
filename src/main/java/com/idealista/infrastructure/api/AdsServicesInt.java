package com.idealista.infrastructure.api;

import java.util.List;

import com.idealista.infrastructure.persistence.InMemoryPersistence;

public interface AdsServicesInt {
	
	public void ScoreAds(InMemoryPersistence memory);
	public List<PublicAd> publicAds(InMemoryPersistence memory);
	public List<QualityAd> qualityAd(InMemoryPersistence memory);
	

}
