package com.capgemini.iplanalysertest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.iplanalyser.IPLAnalyser;
import com.capgemini.opencsvbuilder.CSVException;

public class IPLAnalyserTest {
	private static final String IPL_BATSMAN_FILE_PATH = "C:\\Users\\Pranav\\eclipse-workspace\\IPL Analyser\\src\\test\\resources\\WP DP Data_01 IPL2019FactsheetMostRuns - WP DP Data_01 IPL2019FactsheetMostRuns.csv";
	private static final String IPL_BOWLER_FILE_PATH = "C:\\Users\\Pranav\\eclipse-workspace\\IPL Analyser\\src\\test\\resources\\WP DP Data_02 IPL2019FactsheetMostWkts - WP DP Data_02 IPL2019FactsheetMostWkts.csv";

	private IPLAnalyser iplAnalyser;
	
	@Before
	public void setup() {
		iplAnalyser = new IPLAnalyser();
		try {
			iplAnalyser.loadBatsmanData(IPL_BATSMAN_FILE_PATH);
			iplAnalyser.loadBowlerData(IPL_BOWLER_FILE_PATH);
		} catch (CSVException e) {
		}
	}
	
	@Test
	public void givenAFileWithBatsmanData_WhenLoaded_ShouldReturnTheEntries() {
		iplAnalyser = new IPLAnalyser();
		try {
			int entries = iplAnalyser.loadBatsmanData(IPL_BATSMAN_FILE_PATH);
			assertEquals(100, entries);
		} catch (CSVException e) {}
	}
	
	@Test
	public void givenAFileWithBowlerData_WhenLoaded_ShouldReturnTheEntries() {
		iplAnalyser = new IPLAnalyser();
		try {
			int entries = iplAnalyser.loadBowlerData(IPL_BOWLER_FILE_PATH);
			assertEquals(99, entries);
		} catch (CSVException e) {}
	}
}
