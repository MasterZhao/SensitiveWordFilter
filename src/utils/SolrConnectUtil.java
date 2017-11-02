package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SolrConnectUtil {

	
	public static String getSolrCore(){
		Properties properties=new Properties();
		InputStream in=SolrConnectUtil.class.getClassLoader().getResourceAsStream("solrcore.properties");
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String solrcore=properties.getProperty("solrcore");
		
		return solrcore;
	}
	
}
