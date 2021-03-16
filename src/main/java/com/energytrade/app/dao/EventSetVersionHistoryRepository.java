package com.energytrade.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.energytrade.app.model.EventSetVersionHistory;

@Repository
public interface EventSetVersionHistoryRepository extends JpaRepository<EventSetVersionHistory, Long> {
	@Query("Select COALESCE(max(version),0) from EventSetVersionHistory a where a.allEventSet.eventSetId=?1") 
	int getLatestEventSetVersion(int eventSetId);
	
	@Query("Select a from EventSetVersionHistory a where a.allEventSet.eventSetId=?1") 
	List<EventSetVersionHistory> getByEventSeId(int eventSetId);
	
	@Query("Select a from EventSetVersionHistory a where a.allEventSet.eventSetId=?1 and a.version=?2") 
	EventSetVersionHistory getByEventSeIdAndVersion(int eventSetId, int version);
	
	@Query("Select count(a.allEventSet.eventSetId) from EventSetVersionHistory a where a.allEventSet.eventSetId=?1") 
	int getCountByEventSetId(int eventSetId);
}
