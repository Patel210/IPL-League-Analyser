package com.capgemini.analyserservice;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.capgemini.exceptions.AnalyserException;
import com.capgemini.opencsvbuilder.CSVBuilderFactory;
import com.capgemini.opencsvbuilder.CSVException;
import com.capgemini.opencsvbuilder.ICSVBuilder;
import com.capgemini.pojo.Batsman;
import com.capgemini.pojo.Bowler;

public class IPLAnalyser implements CricketAnalyser {
	
	private List<Batsman> batsmanList = null;
	private List<Bowler> bowlerList = null;

	public IPLAnalyser() {
		batsmanList = new ArrayList<Batsman>();
		bowlerList = new ArrayList<Bowler>();
	}
	
	public int loadBatsmanData(String filePath) throws CSVException {
		try (Reader reader = Files.newBufferedReader(Paths.get(filePath));) {
			ICSVBuilder<Batsman> csvBuilder = CSVBuilderFactory.createCSVBuilder();
			batsmanList.addAll(csvBuilder.getCSVFileList(reader, Batsman.class, filePath)
					   .stream()
					   .distinct()
					   .collect(Collectors.toList()));
			return batsmanList.size();
		} catch (IOException e) {
			throw new CSVException("File Problem encountered", CSVException.ExceptionType.FILE_PROBLEM);
		}
	}
	
	public int loadBowlerData(String filePath) throws CSVException {
		try (Reader reader = Files.newBufferedReader(Paths.get(filePath));) {
			ICSVBuilder<Bowler> csvBuilder = CSVBuilderFactory.createCSVBuilder();
			bowlerList.addAll(csvBuilder.getCSVFileList(reader, Bowler.class, filePath)
					  .stream()
					  .distinct()
					  .collect(Collectors.toList()));
			return bowlerList.size();
		} catch (IOException e) {
			throw new CSVException("File Problem encountered", CSVException.ExceptionType.FILE_PROBLEM);
		}
	}
	
	public List<Double> getTopBattingAverages(int noOfTopAverages) throws AnalyserException {
		checkEmptyList(batsmanList);
		List<Double> topBatsmanAverageList = batsmanList.stream()
						.map(getBattingAverages())
						.sorted(Comparator.reverseOrder())
						.limit(noOfTopAverages)
						.collect(Collectors.toList());
		return topBatsmanAverageList;
	}
	
	public List<Double> getTopBattingStrikeRates(int noOfTopStrikes) throws AnalyserException{
		checkEmptyList(batsmanList);
		List<Double> topBatsmanStrikeRateList = batsmanList.stream()
						.map(player -> player.getStrikeRate())
						.sorted(Comparator.reverseOrder())
						.limit(noOfTopStrikes)
						.collect(Collectors.toList());
		return topBatsmanStrikeRateList;
	}
	
	public List<Batsman> getBatsmenWithMaximumSixes(int noOfTopPlayers) throws AnalyserException{
		checkEmptyList(batsmanList);
		Comparator<Batsman> comparator = Comparator.comparing(Batsman::getSixes)
													  .reversed();
		return getSortedList(batsmanList, comparator, noOfTopPlayers);
	}
	
	public List<Batsman> getBatsmenWithMaximumFours(int noOfTopPlayers) throws AnalyserException{
		checkEmptyList(batsmanList);
		Comparator<Batsman> comparator = Comparator.comparing(Batsman::getFours)
													  .reversed();
		return getSortedList(batsmanList, comparator, noOfTopPlayers);
	}
	
	public List<Batsman> getBatsmenWithBestStrikeRatesAndMaximumBoundaries(int noOfTopPlayers)
																		   throws AnalyserException {
		checkEmptyList(batsmanList);
		Comparator<Batsman> comparator = Comparator.comparing(Batsman::performanceFactor).reversed();
		return getSortedList(batsmanList, comparator, noOfTopPlayers);
	}
	
	public List<Batsman> getBatsmenWithBestAveragesAndBestStrikeRates(int noOfTopPlayers)
																	  throws AnalyserException {
		checkEmptyList(batsmanList);
		Comparator<Batsman> comparatorForAverageRuns = Comparator.comparing(getBattingAverages()).reversed();
		List<Batsman> sortedListByAverages = getSortedList(batsmanList, comparatorForAverageRuns, noOfTopPlayers);
		return getSortedList(sortedListByAverages, Comparator.comparing(Batsman::getStrikeRate).reversed(),
				noOfTopPlayers);
	}
	
	public List<Batsman> getBatsmenWithMaximumRunsWithBestAverages(int noOfTopPlayers) throws AnalyserException {
		checkEmptyList(batsmanList);
		Comparator<Batsman> comparator = Comparator.comparing(Batsman::getRuns).reversed();
		List<Batsman> sortedListByRuns = getSortedList(batsmanList, comparator, noOfTopPlayers);
		return getSortedList(sortedListByRuns, Comparator.comparing(getBattingAverages()).reversed(), noOfTopPlayers);
	}
	
	public List<Double> getTopBowlingAverages(int noOfTopAverages) throws AnalyserException{
		checkEmptyList(bowlerList);
		return bowlerList.stream()
						 .map(getBowlingAverages())
						 .sorted()
						 .limit(noOfTopAverages)
						 .collect(Collectors.toList());
	}
	
	public List<Double> getTopBowlingStrikeRates(int noOfTopStrikeRates) throws AnalyserException{
		checkEmptyList(bowlerList);
		return bowlerList.stream()
				 		 .map(getBowlingStrikeRates())
				 		 .sorted()
				 		 .limit(noOfTopStrikeRates)
				 		 .collect(Collectors.toList());
	}
	
	public List<Bowler> getBowlersWithBestEconomyRate(int noOfTopBowlers) throws AnalyserException{
		checkEmptyList(bowlerList);
		Comparator<Bowler> comparator = Comparator.comparing(Bowler::getEconomy);
		return getSortedList(bowlerList, comparator, noOfTopBowlers);
	}
	
	public List<Bowler> getBowlerWithBestStrikeRateWith5wAnd4w(int noOfTopBowlers) throws AnalyserException {
		checkEmptyList(bowlerList);
		Function<Bowler, Integer> getSumOf5wAnd4w = iplBowler -> iplBowler.getFiveWickets() +
																	iplBowler.getFourWickets();
		Comparator<Bowler> comparator = Comparator.comparing(getSumOf5wAnd4w).reversed();
		List<Bowler> sortedByStrikeRate = getSortedList(bowlerList, Comparator.comparing(getBowlingStrikeRates()),
															bowlerList.size());
		return getSortedList(sortedByStrikeRate, comparator, noOfTopBowlers);
	}
	
	public List<Bowler> getBowlerWithBestBowlingAveragesWithBestStrikingRates(int noOfTopBowlers) 
																			  throws AnalyserException{
		checkEmptyList(bowlerList);
		Comparator<Bowler> comparator = Comparator.comparing(getBowlingAverages());
		List<Bowler> sortedByAverage = getSortedList(bowlerList, comparator,
														noOfTopBowlers);
		return getSortedList(sortedByAverage, Comparator.comparing(getBowlingStrikeRates()), noOfTopBowlers);
	}
	
	public List<Bowler> getBowlerWithMaximumWicketsWithBestBowlingAverages(int noOfTopBowlers) 
																		   throws AnalyserException{
		checkEmptyList(bowlerList);
		Comparator<Bowler> comparator = Comparator.comparing(Bowler::getWickets).reversed();
		List<Bowler> sortedByWickets = getSortedList(bowlerList, comparator, noOfTopBowlers);
		return getSortedList(sortedByWickets, Comparator.comparing(getBowlingAverages()), noOfTopBowlers);
	}
	
	public List<String> getCricketersWithBestBattingAndBowlingAverages(int noOfTopCricketers) throws AnalyserException{
		List<String> list = getCricketersWhoBatsAndBowls();
		Function<String, Double> getAllRounderValue = a -> {
			Double battingAvg = batsmanList.stream()
										   .filter(p -> p.getPlayerName().equals(a))
										   .map(getBattingAverages())
										   .findFirst()
										   .get();
			Double bowlingAvg = bowlerList.stream()
										  .filter(p -> p.getPlayerName().equals(a))
										  .map(getBowlingAverages())
										  .findFirst()
										  .get();
			if (bowlingAvg == 0.0) {
				return 0.0;
			}
			return battingAvg / bowlingAvg;
		};
		return getSortedList(list, Comparator.comparing(getAllRounderValue).reversed(), noOfTopCricketers);
	}
	
	public List<String> getCricketersWithMostWicketsAndRunsHit(int noOfTopCricketers) throws AnalyserException{
		List<String> list = getCricketersWhoBatsAndBowls();
		Function<String, Double> getAllRounderValue = a -> {
			Integer runsHit = batsmanList.stream()
										 .filter(p -> p.getPlayerName().equals(a))
										 .map(Batsman::getRuns)
										 .findFirst()
										 .get();
			Integer wickets = bowlerList.stream()
										.filter(p -> p.getPlayerName().equals(a))
										.map(Bowler::getWickets)
										.findFirst()
										.get();
			double maxRuns = batsmanList.stream().map(Batsman::getRuns).max((i,j) -> i.compareTo(j)).get();
			double maxWickets = bowlerList.stream().map(Bowler::getWickets).max((i,j) -> i.compareTo(j)).get();
			return (runsHit / maxRuns + wickets / maxWickets) / 2.0; 
		};
		return getSortedList(list, Comparator.comparing(getAllRounderValue).reversed(), noOfTopCricketers);
	}
	
	public List<Batsman> getBatsmenWithMostHunderedsWithBestAverage(int noOfTopPlayers) throws AnalyserException{
		checkEmptyList(batsmanList);
		Comparator<Batsman> comparator = Comparator.comparing(Batsman::getHundreds).thenComparing(getBattingAverages()).reversed();
		return getSortedList(batsmanList, comparator, noOfTopPlayers);
	}
	
	private List<String> getCricketersWhoBatsAndBowls() throws AnalyserException{
		checkEmptyList(batsmanList);
		checkEmptyList(bowlerList);
		return batsmanList.stream()
						  .map(Batsman::getPlayerName)
						  .filter(a -> bowlerList.stream()
						  .anyMatch(p -> a.equalsIgnoreCase(p.getPlayerName())))
						  .collect(Collectors.toList());
	}
	
	private <E> List<E> getSortedList(List<E> list, Comparator<E> comparator, int noOfTopPlayers){
		return list.stream()
				   .sorted(comparator)
				   .limit(noOfTopPlayers)
				   .collect(Collectors.toList());
	}
	
	private Function<Batsman, Double> getBattingAverages(){
		Function<Batsman, Double> toGetAverages = p -> Double.parseDouble(p.getAverage());
		return toGetAverages;
	}
	
	private Function<Bowler, Double> getBowlingAverages(){
		Function<Bowler, Double> toGetAverages = p -> Double.parseDouble(p.getAverage());
		return toGetAverages;
	}
	
	private Function<Bowler, Double> getBowlingStrikeRates(){
		Function<Bowler, Double> toGetStrikeRates = p -> Double.parseDouble(p.getStrikeRate());
		return toGetStrikeRates;
	}
	
	private <E> void checkEmptyList(List<E> list) throws AnalyserException {
		if (list.size() == 0) {
			throw new AnalyserException("No Data Available", AnalyserException.ExceptionType.NO_DATA_FOUND);
		}
	}
}
