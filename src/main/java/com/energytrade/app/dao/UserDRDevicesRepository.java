package com.energytrade.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.energytrade.app.model.DrDeviceTypePl;
import com.energytrade.app.model.UserDRDevices;
import com.energytrade.app.model.UserDevice;

@Repository
public interface UserDRDevicesRepository extends JpaRepository<UserDRDevices, Long> {

	@Query("Select COALESCE(max(a.userDrDeviceId),0) from UserDRDevices a")
	int getDrDeviceCount();
	
	@Query("Select  a from DrDeviceTypePl a where a.name =?1")
	DrDeviceTypePl getDrDeviceType(String name);
	
	@Modifying
	@Query("update UserDRDevices set pairedDevice=?1 where userDrDeviceId=?2")
	void updatePairedDevice(int pairedDevice, int deviceId);

}
