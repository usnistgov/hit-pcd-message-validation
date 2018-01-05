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
		try(BufferedReader br= new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + "mdccodes.csv")))) {
		    values = br.lines().map(line -> {return new MDCterm(line.split(",")[0],line.split(",")[1]);}).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return values;
	}
	
	public List<MDCUnitsterm> loadMdcUnitsTerms(){
		
		List<MDCUnitsterm> values = new ArrayList<>();
		try(BufferedReader br= new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + "units.csv")))) {
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
	
	
	public boolean unitIsValid (String refid, String unit) {
		for (MDCUnitsterm mdc : mdcUnitsTerms) {
			if (mdc.getRefid().equals(refid)) {
				if (mdc.getUnits().contains(unit)) {
					return true;
				}else {
					return false;
				}
			}
		}
		//couldn't find refid, no errors
		return true;
	}
	
	
	
}
