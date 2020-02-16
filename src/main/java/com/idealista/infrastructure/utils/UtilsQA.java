package com.idealista.infrastructure.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.idealista.infrastructure.api.PublicAd;
import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.PictureVO;

public class UtilsQA {

	public UtilsQA() {

	}

	public static QualityAd setQalityAds(AdVO ads, List<PictureVO> pics) {

		List<String> urls = pics.stream().filter(a -> ads.getPictures().contains(a.getId())).map(b -> b.getUrl())
				.collect(Collectors.toList());
		final QualityAd newQuality = new QualityAd();

		newQuality.setId(ads.getId());
		newQuality.setTypology(ads.getTypology());
		newQuality.setPictureUrls(urls);
		newQuality.setDescription(ads.getDescription());
		newQuality.setHouseSize(ads.getHouseSize());
		newQuality.setGardenSize(ads.getGardenSize());
		newQuality.setScore(ads.getScore());
		newQuality.setIrrelevantSince(ads.getIrrelevantSince());

		return newQuality;

	}

	public static PublicAd setPublicAds(AdVO ads, List<PictureVO> pics) {

		List<String> urls = pics.stream().filter(a -> ads.getPictures().contains(a.getId())).map(b -> b.getUrl())
				.collect(Collectors.toList());
		final PublicAd newPublic = new PublicAd();

		newPublic.setId(ads.getId());
		newPublic.setTypology(ads.getTypology());
		newPublic.setPictureUrls(urls);
		newPublic.setDescription(ads.getDescription());
		newPublic.setHouseSize(ads.getHouseSize());
		newPublic.setGardenSize(ads.getGardenSize());

		return newPublic;

	}
}
