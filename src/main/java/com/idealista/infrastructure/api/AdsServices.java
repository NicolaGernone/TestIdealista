package com.idealista.infrastructure.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idealista.infrastructure.persistence.InMemoryPersistence;

@Service
public class AdsServices {
	
	@Autowired
	InMemoryPersistence ads;
	
	public int PointsAds() {
		
		return 0;
	}

}
