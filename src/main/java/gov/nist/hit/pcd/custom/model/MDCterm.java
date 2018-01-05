package gov.nist.hit.pcd.custom.model;

public class MDCterm {

	private String termcode;
	private String refid;
	
	
	public MDCterm(String termcode, String mdc) {
		this.termcode = termcode;
		this.refid = mdc;
	}


	public String getTermcode() {
		return termcode;
	}


	public void setTermcode(String termcode) {
		this.termcode = termcode;
	}


	public String getRefid() {
		return refid;
	}


	public void setRefid(String refid) {
		this.refid = refid;
	}


	
	
	
	
}
