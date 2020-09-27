package com.p.bce.shopping.cart.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.p.bce.shopping.cart.servlet.CServletCtxLisener;





public class PropertyUtil {

	static Logger log = Logger.getLogger(PropertyUtil.class);
	Properties propCommonUtility = new Properties();
	private static PropertyUtil thisInsatace;

	private static boolean refreshPropertyFile(String propFileName) {
		try {
			String fileName = CServletCtxLisener.getConfFolderPath()
					+ File.separator + propFileName;
			File f = new File(fileName);
			System.out.println(fileName);

			if (f.exists()) {
				FileInputStream in = new FileInputStream(f);
				thisInsatace.propCommonUtility.load(in);
				log.debug("successfully loded property file : " + propFileName);
				in.close();
				return true;
			} else {
				log.debug("property file not found : " + propFileName);
				return false;
			}
		} catch (FileNotFoundException e) {
			log.error("property file not found : " + propFileName
					+ "\n due to : " + e.getMessage(), e);
			return false;
		} catch (IOException e) {
			log.error("property file not found : " + propFileName
					+ "\n due to : " + e.getMessage(), e);
			return false;
		} catch (Exception e) {
			log.error("property file not found : " + propFileName
					+ "\n due to : " + e.getMessage(), e);
			return false;
		}
	}

	public static PropertyUtil getInstance() {
		if (thisInsatace == null) {
			thisInsatace = new PropertyUtil();
		}
		// refreshPropertyFile();
		return thisInsatace;
	}

	private PropertyUtil() {

	}

	public String getValue(PropertyFile objPropertyFileName, String key,boolean printValue) {

		boolean status = refreshPropertyFile(objPropertyFileName.getName());
		String p = null;
		if (status) {
			p = propCommonUtility.getProperty(key);

			if(p!=null){
				if(printValue){
					log.debug(key + " : " + p);
				}else{
					log.debug(key + " : " + "**** masked due to security reasons");
				}
			}else{
				log.debug(key + " : null ");
			}
			
		}
		return p;
	}
	
	public String getValue(PropertyFile objPropertyFileName, String key) {
		String value=getValue(objPropertyFileName,key,true);
		return value;
	}
}
