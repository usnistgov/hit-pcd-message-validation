package gov.nist.hit.pcd.custom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gov.nist.hit.pcd.custom.model.MDCUnitsterm;
import gov.nist.hit.pcd.custom.model.MDCterm;

public class ResourceHandler {

	private static final String VERSION = "march2021";
	
	private static List<MDCterm> mdcValues;
	private static List<MDCUnitsterm> mdcUnitsTerms;
	
	
	public ResourceHandler() {
		if (mdcValues == null) {
			mdcValues = loadMDC();
		}
		
		if (mdcUnitsTerms == null) {
			mdcUnitsTerms = loadMdcUnitsTerms(); 
		}
		
		
	}
	
	public List<MDCterm> loadMDC(){
		
		List<MDCterm> values = new ArrayList<>();
		try(BufferedReader br= new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/"+VERSION+"/" + "mdccodes.csv")))) {
		    values = br.lines().map(line -> {return new MDCterm(line.split(",")[0],line.split(",")[1]);}).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return values;
	}
	
	public List<MDCUnitsterm> loadMdcUnitsTerms(){
		
		List<MDCUnitsterm> values = new ArrayList<>();
		try(BufferedReader br= new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/"+VERSION+"/" + "units.csv")))) {
		    values = br.lines().map(line -> {return new MDCUnitsterm(line.split(",")); }).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return values;
	}
	
	
	
	
	
	
	public boolean codeExists(String code) {
		for (MDCterm mdc : mdcValues) {
			if (mdc.getTermcode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	
	public boolean dupletIsValid (String code, String refid) {
		for (MDCterm mdc : mdcValues) {
			if (mdc.getRefid().equals(refid)) {
				if (mdc.getTermcode().equals(code)) {
					return true;
				}else {
					return false;
				}
			}
		}
		return false;
	}
	
	
	public boolean unitIsValid (String code, String refid, String unit) {
		// if no unit present, no need to check..
		
		if (unit == null || unit.isEmpty()) {
			return true;
		}
		for (MDCUnitsterm mdc : mdcUnitsTerms) {

			if (mdc.getCode().equals(code) || mdc.getRefid().equals(refid)) {
				for (String u : mdc.getUnits()) {
					if (u.contains("_X_")){
						if (u.equals(unit) || unit.matches(u.replace("_X_", "_.*"))) {
	
							return true;
						}
					}else if (u.contains("_X")){ //suffix
						if (u.equals(unit) || unit.matches(u.replace("_X", "_.+"))) {
	
							return true;
						}
					}else {
						
						if (u.equals(unit)){
							return true;
						}
					}
				}
				return false;
			}
		}
		//couldn't find refid, no errors
		return true;
	}
	
	
	
}
