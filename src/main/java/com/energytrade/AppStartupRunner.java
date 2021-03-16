package com.energytrade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.energytrade.app.dao.DRDao;


@Component
public class AppStartupRunner implements ApplicationRunner {
    
	@Autowired
	DRDao drdao;
	
	public static HashMap<String,String> configValues = new HashMap<>();
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Your application started with option names : {}"+ args.getOptionNames());
        ArrayList<String> al = new ArrayList<>();
        al.add("IR_power_ratio");
        configValues.put("IRpowerRatio",  drdao.getConfigValues(al).get(0).getValue());
        System.out.println("Config Values"+configValues);
    }
}
