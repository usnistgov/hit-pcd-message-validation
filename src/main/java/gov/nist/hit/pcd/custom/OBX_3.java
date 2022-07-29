package gov.nist.hit.pcd.custom;

import java.util.ArrayList;


import hl7.v2.instance.Element;
import hl7.v2.instance.Query;
import hl7.v2.instance.Simple;
import scala.collection.Iterator;
import scala.collection.immutable.List;

public class OBX_3 {

	
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
        List<Element> OBXList = Query.query(e, "3[1]").get();
        if (OBXList == null || OBXList.size() == 0) {
            // OBR-3 is not present
            return messages;
        }
        
        Iterator<Element> it = OBXList.iterator();
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
            	ResourceHandler rh = new ResourceHandler();            	
            	if(!rh.codeExists(OBX3_1)) {
            		messages.add("The provided code "+OBX3_1+ " does not match any known REFID");
            		return messages;
            	}
            	if (rh.dupletIsValid(OBX3_1, OBX3_2)) {
            		return messages;
            	}else {
            		messages.add("The REFID "+OBX3_2+" does not match with the provided code "+OBX3_1);
            		return messages;
            	}
            	
            }else {
            		return messages;
            }
            
        }
        
        return null;
    }	
}
