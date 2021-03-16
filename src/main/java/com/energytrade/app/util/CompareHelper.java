package com.energytrade.app.util;

import java.util.ArrayList;
import java.util.List;

import com.energytrade.app.model.AllEvent;
import com.energytrade.app.model.EventCustomerMapping;

public class CompareHelper {
	
	public static EventCustomerMapping compareData(List<EventCustomerMapping> listOfCustomers, int userId)
	{
		try {
			for (int i=0;i<listOfCustomers.size();i++) {
				if(listOfCustomers.get(i).getAllUser().getUserId() == userId) {
					return listOfCustomers.get(i);
				} 
			}
		} catch(Exception e) {
			
		}
		return null;
	}
	
	public static ArrayList<String> countdata(List<AllEvent> listOfEvents)
	{
		 ArrayList<String> listOfStatus = new ArrayList<String>();
		 int publishedEvents=0, cancelledEvents =0, completedEvents=0;
		try {
			for (int i=0;i<listOfEvents.size();i++) {
				if(listOfEvents.get(i).getEventStatusPl().getEventStatusId() == 2) {
					publishedEvents++;
				}  else if (listOfEvents.get(i).getEventStatusPl().getEventStatusId() == 3 || listOfEvents.get(i).getEventStatusPl().getEventStatusId() == 9) {
					completedEvents++;
				}else if (listOfEvents.get(i).getEventStatusPl().getEventStatusId() == 4) {
					cancelledEvents++;
				}
			}
			listOfStatus.add(Integer.toString(publishedEvents));
			listOfStatus.add(Integer.toString(completedEvents));
			listOfStatus.add(Integer.toString(cancelledEvents));
		} catch(Exception e) {
			
		}
		return listOfStatus;
	}

}
