package com.energytrade.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.regex.Pattern;

import javax.persistence.Query;

public class ValidationUtil {

private final String AUTH_CODE ="c1845f2ac9457678118b873c50d0b70b5e525865";
public int checkAuthentication(String authCode) {
	if(authCode == null) {
		return 0;
	}
	if(authCode.equals(AUTH_CODE)) {
		return 1;
	}
	else {
		return -1;
	}
}

public int checkRequestFormat(HashMap<String,Object> inputDetails) {
	String forecastDate = (String) inputDetails.get("forecast_date");
	ArrayList<HashMap<String,String>> listOfData =(ArrayList<HashMap<String,String>>)inputDetails.get("data");
	if(forecastDate == null || forecastDate.equalsIgnoreCase("")) {
		return -1;
	}
	else if(listOfData == null) {
		return -1;
	}
	else if(listOfData.size() == 0) {
		return -1;
	}
	return 1;
}
}
