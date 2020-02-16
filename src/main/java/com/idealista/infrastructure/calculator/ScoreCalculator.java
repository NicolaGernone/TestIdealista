package com.idealista.infrastructure.calculator;



import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.PictureVO;

public class ScoreCalculator {
	
	//SCORES
	private static final Integer SCORE_1 = -10;
	private static final Integer SCORE_2 = 5;
	private static final Integer SCORE_3 = 10;
	private static final Integer SCORE_4 = 20;
	private static final Integer SCORE_5 = 30;
	private static final Integer SCORE_6 = 40;
	
	//TYPES
	private static final String TYPE_1 = "CHALET";
	private static final String TYPE_2 = "FLAT";
	private static final String TYPE_3 = "GARAGE";
		
	//DESCRIPTION SIZE
	private static final Integer MIN_SIZE = 20;
	private static final Integer MAX_SIZE = 50;
	
	//PIC RESOLUTION
	private static final String HD = "HD";
	//private static final String SD = "SD";
	
	//REGEX
	private static final String REGEX = "[!, ?._'@]+";
	
	
	public ScoreCalculator() {
		
	}
	
	public static AdVO setScoreTotal(AdVO ads, List<PictureVO> pics) {
		
		Integer totalScore = 0;
		Date irrilevance = new Date();
		
		Integer scorePics = scorePics(pics.stream().filter(x -> ads.getPictures().contains(x.getId()))
												   .collect(Collectors.toList()));
		Integer scoreDesc = descScore(ads);
		Integer scoreComplete = completeAds(ads);
		
		totalScore = scorePics + scoreDesc + scoreComplete;
		
		ads.setScore(Math.max(0, totalScore));
		
		if(totalScore <= SCORE_6) {
			
			ads.setIrrelevantSince(irrilevance);
			
		}
		
		return ads;
	}
	
	public static Integer scorePics(List<PictureVO> pics) {
		
		if(!pics.isEmpty()) {
			
			Integer picScore = 0;
			
			picScore = pics.stream().mapToInt(x -> HD.equals(x.getQuality())? SCORE_4 : SCORE_3).sum();
			
			return picScore;
			
		} else {
			
			return SCORE_1;
			
		}

	}
	
	public static Integer descScore(AdVO ads) {
		
		if(!ads.getDescription().isEmpty()) {
			
			Integer matchAdgScore = 0;
			Integer sizeDescScore = 0;
			String[] splitter = ads.getDescription().split(REGEX);
			List<String> words = Arrays.asList(splitter);
			matchAdgScore = words.stream()
										.filter(x -> x.toUpperCase()
										.matches("ÁTICO|NUEVO|REFORMADO|CÉNTRICO|LUMINOSO"))
								  		.mapToInt(v -> SCORE_2)
									    .sum();
			
			if(TYPE_1.equals(ads.getTypology()) && words.size() > MAX_SIZE) {
				
				sizeDescScore = SCORE_4;
				
			} else if(TYPE_2.equals(ads.getTypology()) && words.size() >= MIN_SIZE && words.size() < MAX_SIZE ) {
				
				sizeDescScore = SCORE_3;
				
			} else if(TYPE_2.equals(ads.getTypology()) && words.size() >= MAX_SIZE) {
				
				sizeDescScore = SCORE_5;
				
			} else {
				
				sizeDescScore = 0;
			}
			
			return matchAdgScore + sizeDescScore + SCORE_2;
			
		} else {
			
			return 0;
		}
	}
	
	public static Integer completeAds(AdVO ads) {
				
		if(TYPE_3.equals(ads.getTypology()) && !ads.getPictures().isEmpty()) {
			
			return SCORE_6;
			
		} else if(TYPE_1.equals(ads.getTypology()) && !ads.getPictures().isEmpty() && ads.getHouseSize() != null && ads.getGardenSize() != null) {
			
			return SCORE_6;
			
		} else if(TYPE_2.equals(ads.getTypology()) && !ads.getPictures().isEmpty() && ads.getHouseSize() != null) {
			
			return SCORE_6;
			
		} else {
			
			return 0;
		}
			
	}

}
