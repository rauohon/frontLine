package com.rauOhon.frontLine.cmmn.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FnlMap {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	Map<Object, Object> map = new HashMap<Object, Object>();
	
	public Object put (Object key, Object value) {
		if (value instanceof Date) {
			SimpleDateFormat app_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put(CamelUtil.convert2CamelCase("app_"+(String)key), app_.format(value));
			
			SimpleDateFormat app2_ = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			map.put(CamelUtil.convert2CamelCase("app2_"+(String)key), app2_.format(value));
		}
		
		Object resultObject = null;
		resultObject = map.put(CamelUtil.convert2CamelCase((String)key), value);
		
		return resultObject;
	}

    public Object get (Object key) {
        return map.get(key);
    }
    
    public String getString (Object key) {
    	Object objResult = get(key);
    	String result = "";
    	if (objResult != null) {
    		result = objResult.toString();
    	}
    	return result;
    }
    
    public int getInt (Object key) {
    	String objResult = getString(key);
    	int result = -1;
    	try{
    		result = Integer.parseInt(objResult) ;
        }catch(Exception e){
        	log.info("cannot read int data");
        }
    	return result;
    }
    
    
    public boolean isEmpty () {
        return map.isEmpty();
    }
     
    public Map<Object, Object> getMap (){
        return map;
    }
    
    public void clear () {
        map.clear();
    }
     
    public boolean containsKey (String key) {
        return map.containsKey(key);
    }
     
    public boolean containsValue (Object value) {
        return map.containsValue(value);
    }
     
    public Object remove (Object key) {
        return map.remove(key);
    }
     
    public void putAll (Map<? extends Object, ? extends Object> m) {
        map.putAll(m);
    }
    
    public void loggigMap () {
    	Iterator<Object> mapIter = map.keySet().iterator();
    	StringBuilder sb = new StringBuilder();

    	while (mapIter.hasNext()) {
    		String key = (String) mapIter.next();
    		String value = (String) map.get(key);
    		sb.append("| key : " + key + ", value : " + value + " || ");
    	}
    	
		log.debug("| =============================================== |");
    	log.debug(sb.toString());
		log.debug("| =============================================== |");
    }
    
    public List<HashMap<Object, Object>> getSetList (List<FnlMap> list) {
    	List<HashMap<Object, Object>> resultList = new ArrayList<HashMap<Object, Object>>();
    	
    	if (list == null || list.size() == 0) {
    		return null;
    	}
    	
    	Iterator<FnlMap> listIter = list.iterator();
    	
    	while (listIter.hasNext()) {
    		resultList.add(getSetMap(listIter.next()));
    	}
    	
    	return resultList;
    }
    
    public HashMap<Object, Object> getSetMap (FnlMap fnlMap) {
    	HashMap<Object, Object> resultHashMap = new HashMap<Object, Object>(); 
    	
    	if (fnlMap == null) {
    		return null;
    	}
    	
    	Iterator<Object> mapIter = fnlMap.getMap().keySet().iterator();
    	
    	while (mapIter.hasNext()) {
    		String key = mapIter.next().toString();
    		resultHashMap.put(key, fnlMap.get(key));
    	}
    	
    	return resultHashMap;
    }
    
}
