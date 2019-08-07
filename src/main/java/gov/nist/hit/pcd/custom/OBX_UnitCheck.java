package gov.nist.hit.pcd.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import hl7.v2.instance.Element;
import hl7.v2.instance.Query;
import hl7.v2.instance.Simple;
import scala.collection.Iterator;
import scala.collection.immutable.List;

public class OBX_UnitCheck {

	
    /*
     * OBX-3: If OBX-3.3 is MDC, check if code/refid is valid .
     */

    /**
     * @param e
     *        OBX context
     * @return true if OBX-3.3 not MDC, true if OBX-3.2 and OBX-3.1 are valid, false otherwise
     */
		
	public java.util.List<String> assertionWithCustomMessages(hl7.v2.instance.Element e) {
		java.util.List<String> messages = new ArrayList<String>();
        ResourceHandler rh = new ResourceHandler();

		boolean checkOne = false;
		boolean checkTwo = false;
		
		
        List<Element> OBX3List = Query.query(e, "3[1]").get();
        if (OBX3List == null || OBX3List.size() == 0) {
            // OBX-3 is not present
            return messages;
        }
        
        List<Element> OBX6List = Query.query(e, "6[1]").get();
        if (OBX6List == null || OBX6List.size() == 0) {
            // OBX-6 is not present
            return messages;
        }
        
        Iterator<Element> it = OBX3List.iterator();
        while (it.hasNext()) {
            Element next = it.next();
            List<Simple> OBX3_1List = Query.queryAsSimple(next, "1[1]").get();
            List<Simple> OBX3_2List = Query.queryAsSimple(next, "2[1]").get();
            List<Simple> OBX3_3List = Query.queryAsSimple(next, "3[1]").get();

            String OBX3_1 = OBX3_1List.size() > 0 ? OBX3_1List.apply(0).value().raw()   : "";
            String OBX3_2 = OBX3_2List.size() > 0 ? OBX3_2List.apply(0).value().raw()   : "";
            String OBX3_3 = OBX3_3List.size() > 0 ? OBX3_3List.apply(0).value().raw()   : "";
               
            // ignore 0 code terms or MDCX terms 
            if (OBX3_1.equals("0") || OBX3_2.startsWith("MDCX_")) {
            		return messages;
            }
            
            
            if (OBX3_3.equals("MDC")) {
            
            	
            	
            	 Iterator<Element> it6 = OBX6List.iterator();
            	 while (it6.hasNext()) {
                     Element next6 = it6.next();
                     

                     //primary 
                     List<Simple> OBX6_1List = Query.queryAsSimple(next6, "1[1]").get();
                     List<Simple> OBX6_2List = Query.queryAsSimple(next6, "2[1]").get();
                     List<Simple> OBX6_3List = Query.queryAsSimple(next6, "3[1]").get();
                     String OBX6_1 = OBX6_2List.size() > 0 ? OBX6_1List.apply(0).value().raw()   : "";
                     String OBX6_2 = OBX6_2List.size() > 0 ? OBX6_2List.apply(0).value().raw()   : "";
                     String OBX6_3 = OBX6_3List.size() > 0 ? OBX6_3List.apply(0).value().raw()   : "";
                 	if (OBX6_3.equals("MDC")) {
                 		if (!rh.dupletIsValid(OBX6_1, OBX6_2)) {                    		
	                    		messages.add("The Unit REFID "+OBX6_2+" does not match with the provided code "+OBX6_1);
	                    	}
                 	}
                    
                     
                     //alternate
                     List<Simple> OBX6_4List = Query.queryAsSimple(next6, "4[1]").get();
                     List<Simple> OBX6_5List = Query.queryAsSimple(next6, "5[1]").get();
                     List<Simple> OBX6_6List = Query.queryAsSimple(next6, "6[1]").get();
                     String OBX6_4 = OBX6_4List.size() > 0 ? OBX6_4List.apply(0).value().raw()   : "";
                     String OBX6_5 = OBX6_5List.size() > 0 ? OBX6_5List.apply(0).value().raw()   : "";
                     String OBX6_6 = OBX6_6List.size() > 0 ? OBX6_6List.apply(0).value().raw()   : "";

                     if (OBX6_6.equals("MDC")) {
                  		if (!rh.dupletIsValid(OBX6_4, OBX6_5)) {                    		
 	                    		messages.add("The Unit REFID "+OBX6_5+" does not match with the provided code "+OBX6_4);
 	                    	}
                  	}
                     
                     checkOne = rh.unitIsValid(OBX3_1, OBX3_2, OBX6_2);
                     checkTwo = rh.unitIsValid(OBX3_1, OBX3_2, OBX6_5);
                                          
		            	if (checkOne && checkTwo) {
		            		return messages;
		            	}else {
		            		if (!checkOne) {
		            			messages.add("The provided primary unit ("+OBX6_2+") associated with "+ OBX3_2 + " is not valid");
		            		}
		            		if (!checkTwo) {
		            			messages.add("The provided alternate unit ("+OBX6_5+") associated with "+ OBX3_2 + " is not valid");
		            		}
		            		return messages;
		            	}
            	 }
            	
            	
	            
	            	
	            }else {
	            		return messages;
            }
            
        }
        
        return messages;
    }	
}
