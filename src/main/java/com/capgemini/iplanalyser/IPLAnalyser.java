package com.capgemini.iplanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.capgemini.exceptions.IPLAnalyserException;
import com.capgemini.iplpojo.IPLBatsman;
import com.capgemini.iplpojo.IPLBowler;
import com.capgemini.opencsvbuilder.CSVBuilderFactory;
import com.capgemini.opencsvbuilder.CSVException;
import com.capgemini.opencsvbuilder.ICSVBuilder;

public class IPLAnalyser {
	private List<IPLBatsman> batsmanList = null;
	private List<IPLBowler> bowlerList = null;

	public IPLAnalyser() {
		batsmanList = new ArrayList<IPLBatsman>();
		bowlerList = new ArrayList<IPLBowler>();
	}
	public int loadBatsmanData(String filePath) throws CSVException {
		try (Reader reader = Files.newBufferedReader(Paths.get(filePath));) {
			ICSVBuilder<IPLBatsman> csvBuilder = CSVBuilderFactory.createCSVBuilder();
			batsmanList.addAll(csvBuilder.getCSVFileList(reader, IPLBatsman.class, filePath)
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
			ICSVBuilder<IPLBowler> csvBuilder = CSVBuilderFactory.createCSVBuilder();
			bowlerList.addAll(csvBuilder.getCSVFileList(reader, IPLBowler.class, filePath)
					  .stream()
					  .distinct()
					  .collect(Collectors.toList()));
			return bowlerList.size();
		} catch (IOException e) {
			throw new CSVException("File Problem encountered", CSVException.ExceptionType.FILE_PROBLEM);
		}
	}
	
	public List<Double> getTopBattingAverages(int noOfTopAverages) throws IPLAnalyserException {
		if(batsmanList.size() == 0) {
			throw new IPLAnalyserException("No Data Available", IPLAnalyserException.ExceptionType.NO_DATA_FOUND);
		}
		List<Double> topBatsmanAverageList = batsmanList.stream()
						.map(player -> Double.parseDouble(player.getAverage()))
						.sorted(Comparator.reverseOrder())
						.limit(noOfTopAverages)
						.collect(Collectors.toList());
		return topBatsmanAverageList;
	}
	
	public List<Double> getTopStrikeRates(int noOfTopStrikes) throws IPLAnalyserException{
		if(batsmanList.size() == 0) {
			throw new IPLAnalyserException("No Data Available", IPLAnalyserException.ExceptionType.NO_DATA_FOUND);
		}
		List<Double> topBatsmanStrikeRateList = batsmanList.stream()
						.map(player -> player.getStrikeRate())
						.sorted(Comparator.reverseOrder())
						.limit(noOfTopStrikes)
						.collect(Collectors.toList());
		return topBatsmanStrikeRateList;
	}
}
