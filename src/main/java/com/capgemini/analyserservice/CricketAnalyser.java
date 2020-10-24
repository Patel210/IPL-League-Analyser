package com.capgemini.analyserservice;

import java.util.List;

import com.capgemini.exceptions.AnalyserException;
import com.capgemini.opencsvbuilder.CSVException;
import com.capgemini.pojo.Batsman;
import com.capgemini.pojo.Bowler;

public interface CricketAnalyser {
	
	public int loadBatsmanData(String filePath) throws CSVException;
	public int loadBowlerData(String filePath) throws CSVException;
	public List<Double> getTopBattingAverages(int noOfTopAverages) throws AnalyserException;
	public List<Double> getTopBattingStrikeRates(int noOfTopStrikes) throws AnalyserException;
	public List<Batsman> getBatsmenWithMaximumSixes(int noOfTopPlayers) throws AnalyserException;
	public List<Batsman> getBatsmenWithMaximumFours(int noOfTopPlayers) throws AnalyserException;
	public List<Batsman> getBatsmenWithBestStrikeRatesAndMaximumBoundaries(int noOfTopPlayers)
			throws AnalyserException;
	public List<Batsman> getBatsmenWithBestAveragesAndBestStrikeRates(int noOfTopPlayers)
			throws AnalyserException;
	public List<Batsman> getBatsmenWithMaximumRunsWithBestAverages(int noOfTopPlayers) throws AnalyserException;
	public List<Double> getTopBowlingAverages(int noOfTopAverages) throws AnalyserException;
	public List<Double> getTopBowlingStrikeRates(int noOfTopStrikeRates) throws AnalyserException;
	public List<Bowler> getBowlersWithBestEconomyRate(int noOfTopBowlers) throws AnalyserException;
	public List<Bowler> getBowlerWithBestStrikeRateWith5wAnd4w(int noOfTopBowlers) throws AnalyserException;
	public List<Bowler> getBowlerWithBestBowlingAveragesWithBestStrikingRates(int noOfTopBowlers) 
			throws AnalyserException;
	public List<Bowler> getBowlerWithMaximumWicketsWithBestBowlingAverages(int noOfTopBowlers) 
			throws AnalyserException;
	public List<String> getCricketersWithBestBattingAndBowlingAverages(int noOfTopCricketers) throws AnalyserException;
	public List<String> getCricketersWithMostWicketsAndRunsHit(int noOfTopCricketers) throws AnalyserException;
}
