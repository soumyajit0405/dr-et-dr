package com.energytrade.app.dao;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.energytrade.app.model.AllDso;
import com.energytrade.app.model.AllElectricityBoard;
import com.energytrade.app.model.AllState;
import com.energytrade.app.model.AllUser;
import com.energytrade.app.model.KiotUserMapping;
import com.energytrade.app.model.LocalityPl;
import com.energytrade.app.model.StateBoardMapping;
import com.energytrade.app.model.UserRolesPl;
import com.energytrade.app.model.UserTypePl;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface KiotUserMappingRepository extends JpaRepository<KiotUserMapping, Long>
{
    
    @Query("select a from KiotUserMapping a where a.contractNumber=?1")
    KiotUserMapping getKiotUserMappingByContract(String contract);
          
}