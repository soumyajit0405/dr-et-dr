package com.energytrade.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.energytrade.app.model.UserDRDevices;
import com.energytrade.app.model.UserDevice;

@Repository
public interface UserDevicesRepository extends JpaRepository<UserDevice, Long> {

	@Query("Select a from UserDevice a where a.userDeviceId=?1")
	UserDevice getUserDevice(int userDeviceId);

	@Query("Select a from UserDRDevices a where a.userDrDeviceId=?1")
	UserDRDevices getUserDRDevice(int userDeviceId);

	@Query("Select COALESCE(max(a.userDrDeviceId),0) from UserDRDevices a")
	int getDrDeviceCount();

}
