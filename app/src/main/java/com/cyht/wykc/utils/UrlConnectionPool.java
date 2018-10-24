package com.cyht.wykc.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

public class UrlConnectionPool {
    
	private static String TAG = UrlConnectionPool.class.toString();
	
	public static UrlConnectionPool urlConnectionPool;
	
	private LinkedList<HttpURLConnection> connectPools = new LinkedList<HttpURLConnection>();
	
	private UrlConnectionPool(){
		
	}
	
	public static UrlConnectionPool getInstanceConnectPool(){
		
		if(urlConnectionPool == null){
			urlConnectionPool = new UrlConnectionPool();
		}
		
		return urlConnectionPool;
	}
	
	public static HttpURLConnection createConnect(String urlStr) throws Exception {
        
        if(urlStr == null){
            throw new NullPointerException();
        }        
        
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);  
        conn.setRequestMethod("GET");  
        conn.setDoInput(true);
        
        return conn;
    }
	
	public synchronized HttpURLConnection addConnect(String urlStr) throws Exception {
		HttpURLConnection connect = createConnect(urlStr);
		connectPools.addLast(connect);
		return connect;
	}
	
	public synchronized void remove(HttpURLConnection connect){
		
		if(connect == null){
			return;
		}
		
		connect.disconnect();
		connectPools.remove(connect);
		connect = null;
	}
	
	public synchronized void removeAll(){
		
//		Log.d(TAG+"--removeAll:  ", "Remove all HttpUrlConnection");
		if(connectPools.isEmpty()){
			return;
		}
		
		for (int i = 0; i < connectPools.size(); i++) {
			remove(connectPools.get(i));
		}
		
		connectPools.clear();
	}
}
