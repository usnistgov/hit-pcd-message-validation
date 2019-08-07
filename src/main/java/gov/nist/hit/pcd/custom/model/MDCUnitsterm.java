package gov.nist.hit.pcd.custom.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MDCUnitsterm {

	private String code;
	private String refid;

	private List<String> units;

	
	

	public MDCUnitsterm(String[] codeRefAndunits) {
		code = codeRefAndunits[0];
		refid = codeRefAndunits[1];		
		units = new ArrayList<>(Arrays.asList(codeRefAndunits));
		units.remove(0); // remove code
		units.remove(0); // remoce refid
	}


	public MDCUnitsterm(String code,String refid,List<String>  units) {
		super();
		this.code = code;
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


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}

	

	
	
	
	
}
