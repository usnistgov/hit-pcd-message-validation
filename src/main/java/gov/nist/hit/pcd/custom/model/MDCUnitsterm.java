package gov.nist.hit.pcd.custom.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MDCUnitsterm {

	private List<String> units;
	private String refid;
	
	

	public MDCUnitsterm(String[] refAndunits) {
		refid = refAndunits[0];
		units = new ArrayList<>(Arrays.asList(refAndunits));
		units.remove(0);
	}


	public MDCUnitsterm(String refid,List<String>  units) {
		super();
		this.units = units;
		this.refid = refid;
	}


	public List<String>  getUnits() {
		return units;
	}


	public void setUnits(List<String>  units) {
		this.units = units;
	}


	public String getRefid() {
		return refid;
	}


	public void setRefid(String refid) {
		this.refid = refid;
	}


	
	
	
	
}
