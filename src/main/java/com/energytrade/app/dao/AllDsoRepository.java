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
import com.energytrade.app.model.LocalityPl;
import com.energytrade.app.model.StateBoardMapping;
import com.energytrade.app.model.UserRolesPl;
import com.energytrade.app.model.UserTypePl;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AllDsoRepository extends JpaRepository<AllDso, Long>
{
    
    @Query("Select a from AllDso a where a.emailId=?1 and a.password=?2 ")
    AllDso loginDSOUser(String email,String password );
    
    @Query("Select a from AllDso a where a.dsoId=?1 ")
    AllDso getDsoDetails(int dsoId );
          
}