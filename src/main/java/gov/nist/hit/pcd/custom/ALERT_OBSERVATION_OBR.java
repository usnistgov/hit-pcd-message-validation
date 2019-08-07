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

public class ALERT_OBSERVATION_OBR {

	public java.util.List<String> assertionWithCustomMessages(hl7.v2.instance.Element e) {
		java.util.List<String> messages = new ArrayList<String>();
//		System.out.println("get in");
		List<Element> OBXList = Query.query(e, "2[*].1[1]").get();
//		System.out.println(OBXList);

		if (OBXList.size() >= 2) {
			String OBX3_1 = getSimpleValue(Query.queryAsSimple(OBXList.apply(0), "3[1].2[1]").get());
			String OBX3_2 = getSimpleValue(Query.queryAsSimple(OBXList.apply(1), "3[1].2[1]").get());
//			String OBX3_3 = getSimpleValue(Query.queryAsSimple(OBXList.apply(2), "3[1].2[1]").get());

			if (!OBX3_1.matches(".*_MDS")) {
				messages.add("Missing Medical Device System (MDS) information");
				return messages;
			}
			if (!OBX3_2.matches(".*_VMD")) {
				messages.add("Missing Virtual Medical Devices (VMD) information");
				return messages;
			}
//			if (!OBX3_3.matches(".*_CHAN")) {
//				return false;
//			}
			
		}

		//
		// Iterator<Element> it = OBXList.iterator();
		// while (it.hasNext()) {
		// Element next = it.next();
		// List<Simple> OBX3_1List = Query.queryAsSimple(next, "1[1]").get();
		// List<Simple> OBX3_2List = Query.queryAsSimple(next, "2[1]").get();
		// List<Simple> OBX3_3List = Query.queryAsSimple(next, "3[1]").get();
		//
		// String OBX3_1 = OBX3_1List.size() > 0 ? OBX3_1List.apply(0).value().raw() :
		// "";
		// String OBX3_2 = OBX3_2List.size() > 0 ? OBX3_2List.apply(0).value().raw() :
		// "";
		// String OBX3_3 = OBX3_3List.size() > 0 ? OBX3_3List.apply(0).value().raw() :
		// "";
		//
		// // ignore 0 code terms or MDCX terms
		// if (OBX3_1.equals("0") || OBX3_2.startsWith("MDCX_")) {
		// return true;
		// }
		//
		//
		// if (OBX3_3.equals("MDC")) {
		// ResourceHandler rh = new ResourceHandler();
		// if (rh.dupletIsValid(OBX3_1, OBX3_2)) {
		// return true;
		// }else {
		// return false;
		// }
		//
		// }else {
		// return true;
		// }
		//
		// }

		return messages;

	}

	private String getSimpleValue(List<Simple> simpleElementList) {
		if (simpleElementList.size() > 1) {
			throw new IllegalArgumentException("Invalid List size : " + simpleElementList.size());
		}
		if (simpleElementList.size() == 0) {
			return null;
		}
		// only get first element
		return simpleElementList.apply(0).value().raw();
	}

}
