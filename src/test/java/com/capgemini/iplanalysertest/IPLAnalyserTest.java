package com.capgemini.iplanalysertest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.analyserservice.CricketAnalyser;
import com.capgemini.analyserservice.IPLAnalyser;
import com.capgemini.exceptions.AnalyserException;
import com.capgemini.opencsvbuilder.CSVException;
import com.capgemini.pojo.Batsman;
import com.capgemini.pojo.Bowler;

public class IPLAnalyserTest {
	private static final String IPL_BATSMAN_FILE_PATH = "C:\\Users\\Pranav\\eclipse-workspace\\IPL Analyser\\src\\test\\resources\\WP DP Data_01 IPL2019FactsheetMostRuns - WP DP Data_01 IPL2019FactsheetMostRuns.csv";
	private static final String IPL_BOWLER_FILE_PATH = "C:\\Users\\Pranav\\eclipse-workspace\\IPL Analyser\\src\\test\\resources\\WP DP Data_02 IPL2019FactsheetMostWkts - WP DP Data_02 IPL2019FactsheetMostWkts.csv";

	private CricketAnalyser cricketAnalyser;
	
	@Before
	public void setup() {
		cricketAnalyser = new IPLAnalyser();
		try {
			cricketAnalyser.loadBatsmanData(IPL_BATSMAN_FILE_PATH);
			cricketAnalyser.loadBowlerData(IPL_BOWLER_FILE_PATH);
		} catch (CSVException e) {
		}
	}
	
	@Test
	public void givenCSVBatsmenFileWithBatsmanData_WhenLoaded_ShouldReturnTheEntries() {
		cricketAnalyser = new IPLAnalyser();
		try {
			int entries = cricketAnalyser.loadBatsmanData(IPL_BATSMAN_FILE_PATH);
			assertEquals(100, entries);
		} catch (CSVException e) {}
	}
	
	@Test
	public void givenCSVBowlerFileWithBowlerData_WhenLoaded_ShouldReturnTheEntries() {
		cricketAnalyser = new IPLAnalyser();
		try {
			int entries = cricketAnalyser.loadBowlerData(IPL_BOWLER_FILE_PATH);
			assertEquals(99, entries);
		} catch (CSVException e) {}
	}
	
	@Test
	public void givenCSVBatsmenFileWhenLoadedToGetTopBattingAverages_ShouldReturnCorrectResult() {
		try {
			List<Double> list = cricketAnalyser.getTopBattingAverages(3);
			assertEquals(83.2, list.get(0), 0.0);
		} catch (AnalyserException e) {}
	}
	
	@Test
	public void givenCSVBatsmenFileWhenLoadedToGetTopStrikeRatesOfTheBatsman_ShouldReturnCorrectResult() {
		try {
			List<Double> list = cricketAnalyser.getTopBattingStrikeRates(3);
			assertEquals(333.33, list.get(0), 0.0);
		} catch (AnalyserException e) {}
	}
	
	@Test
	public void givenCSVBatsmenFileWhenLoadedToGetTopBatsmenWithMaximumSixes_ShouldReturnCorrectResult() {
		try {
			List<Batsman> topSixHitters = cricketAnalyser.getBatsmenWithMaximumSixes(3);
			assertEquals("Andre Russell", topSixHitters.get(0).getPlayerName());
		} catch (AnalyserException e) {}
	}
	
	@Test
	public void givenCSVBatsmenFileWhenLoadedToGetTopBatsmenWithMaximumFours_ShouldReturnCorrectResult() {
		try {
			List<Batsman> topFourHitters = cricketAnalyser.getBatsmenWithMaximumFours(3);
			assertEquals("Shikhar Dhawan", topFourHitters.get(0).getPlayerName());
		} catch (AnalyserException e) {}
	}
	
	@Test
	public void givenCSVBatsmenFileWhenLoadedToGetTopBatsmenWithBestStrikeRateAndMaximumFoursAndSixes_ShouldReturnCorrectResult() {
		try {
			List<Batsman> topStrikeRateWithMaximumBoundaries = cricketAnalyser
					.getBatsmenWithBestStrikeRatesAndMaximumBoundaries(3);
			assertEquals("Andre Russell", topStrikeRateWithMaximumBoundaries.get(0).getPlayerName());
		} catch (AnalyserException e) {}
	}
	
	@Test
	public void givenCSVBatsmenFileWhenLoadedToGetTopBatsmenWhoHadBestAveragesWithBestStrikingRates_ShouldReturnCorrectResult() {
		try {
			List<Batsman> bestAveragesWithBestStrikeRateCricketers = cricketAnalyser
					.getBatsmenWithBestAveragesAndBestStrikeRates(3);
			assertEquals("Andre Russell", bestAveragesWithBestStrikeRateCricketers.get(0).getPlayerName());
		} catch (AnalyserException e) {}
	}
	
	@Test
	public void givenCSVBatsmenFileWhenLoadedToGetTopBatsmenWhoHadMaximumRunsWithBestAverages_ShouldReturnCorrectResult() {
		try {
			List<Batsman> maximumRunsWithBestAveragesCricketers = cricketAnalyser
					.getBatsmenWithMaximumRunsWithBestAverages(3);
			assertEquals("David Warner", maximumRunsWithBestAveragesCricketers.get(0).getPlayerName());
		} catch (AnalyserException e) {}
	}
	
	@Test
	public void givenCSVBowlerFileWhenLoadedToGetTopBowlingAverages_ShouldReturnCorrectResult() {
		try {
			List<Double> topBowlingAverages = cricketAnalyser.getTopBowlingAverages(3);
			List<Double> expectedTopBowlingAverages = Arrays.asList(new Double[]{11.0, 14.0, 14.5});
			assertEquals(expectedTopBowlingAverages, topBowlingAverages);
		} catch (AnalyserException e) {}
	}
	
	@Test
	public void givenCSVBowlerFileWhenLoadedToGetTopBowlerStrikeRates_ShouldReturnCorrectResult() {
		try {
			List<Double> topBowlerStrikeRates = cricketAnalyser.getTopBowlingStrikeRates(3);
			List<Double> expectedTopBowlingStrikeRates = Arrays.asList(new Double[]{8.66, 10.75, 11.0});
			assertEquals(expectedTopBowlingStrikeRates, topBowlerStrikeRates);
		} catch (AnalyserException e) {}
	}
	
	@Test
	public void givenCSVBowlerFileWhenLoadedToGetBowlersWithBestEconomyRates_ShouldReturnCorrectResult() {
		try {
			List<Bowler> bestEconomyRatesBowlers = cricketAnalyser.getBowlersWithBestEconomyRate(3);
			assertEquals("Shivam Dube", bestEconomyRatesBowlers.get(0).getPlayerName());
		} catch(AnalyserException e) {}
	}
	
	@Test
	public void givenCSVBowlerFileWhenLoadedToGetBowlersWithBestStrikeRatesWith5wAnd4ws_ShouldRetunrCorrectResult() {
		try {
			List<Bowler> bestStrikeRatesAndBest5wsAnd4wsBowlers = cricketAnalyser.getBowlerWithBestStrikeRateWith5wAnd4w(3);
			assertEquals("Kagiso Rabada", bestStrikeRatesAndBest5wsAnd4wsBowlers.get(0).getPlayerName());
		} catch(AnalyserException e) {}
	}
	
	@Test
	public void givenCSVBowlerFileWhenLoadedToGetBowlersWithBestAveragesWithBestStrikeRates_ShouldReturnCorrectResult() {
		try {
			List<Bowler> bestAveragesWithBestStrikeRateBowlers = cricketAnalyser.getBowlerWithBestBowlingAveragesWithBestStrikingRates(3);
			assertEquals("Alzarri Joseph", bestAveragesWithBestStrikeRateBowlers.get(0).getPlayerName());
		} catch(AnalyserException e) {}
	}
	
	@Test
	public void givenCSVBowlerFile_WhenLoadedToGetBowlersWithMaxWicketsWithBestBowlingAverages_ThenReturnCorrectResult() {
		try {
			List<Bowler> maxWicketsWithBestAveragesBowlers = cricketAnalyser.getBowlerWithMaximumWicketsWithBestBowlingAverages(3);
			assertEquals("Kagiso Rabada", maxWicketsWithBestAveragesBowlers.get(0).getPlayerName());
		} catch(AnalyserException e) {}
	}
	
	@Test
	public void givenBothCSVFiles_WhenLoadedToGetCricketersWithBestBattingAndBowlingAverages_ThenReturnCorrectResult() {
		try {
			List<String> cricketersWithBestBattingAndBowlingAverages = cricketAnalyser.getCricketersWithBestBattingAndBowlingAverages(5);
			assertEquals("Andre Russell", cricketersWithBestBattingAndBowlingAverages.get(0));
		} catch(AnalyserException e) {}
	}
}
