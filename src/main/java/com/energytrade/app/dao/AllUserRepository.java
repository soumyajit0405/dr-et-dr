package com.energytrade.app.dao;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.energytrade.app.model.AllElectricityBoard;
import com.energytrade.app.model.AllState;
import com.energytrade.app.model.AllUser;
import com.energytrade.app.model.DRContracts;
import com.energytrade.app.model.GeneralConfig;
import com.energytrade.app.model.LocalityPl;
import com.energytrade.app.model.StateBoardMapping;
import com.energytrade.app.model.UserAccessTypeMapping;
import com.energytrade.app.model.UserRolesPl;
import com.energytrade.app.model.UserTypePl;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AllUserRepository extends JpaRepository<AllUser, Long>
{
    
    @Query("Select count(a.userId) from AllUser a where a.email=?1 and a.password=?2 ")
    int loginDSOUser(String email,String password );
       
    @Query("Select a from AllUser a where a.email=?1")
    AllUser getUserBymail(String email );
    
    @Query("Select a from AllUser a where a.userId=?1")
    AllUser getUserById(int userId );
    
    @Query("Select a from AllUser a, DRContracts b, UserAccessTypeMapping c where a.drContractNumber = b.contractNumber and a.userId=c.allUser.userId and c.userTypepl.userTypeId =2 and a.drContractNumber is not null and a.softdeleteflag = 0")
    List<AllUser> getAllDrCustomers( );
    
    @Query("Select a from DRContracts a where a.contractNumber =?1")
    DRContracts getDrContracts( String contractNumber);
    
    @Query("Select a from UserTypePl a where a.userTypeName=?1")
    UserTypePl getUserType(String type );
    
    @Query("Select a from GeneralConfig a where a.name in ?1")
    ArrayList<GeneralConfig> getConfigValues(ArrayList<String> configName);
}