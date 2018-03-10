package com.thinkjoy.common.util.xmlutil;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xufei on 2017/12/13.
 */
public class MapAdapter  extends XmlAdapter<MapAdapter.AdaptedMap, Map<String, String>> {

    public static class AdaptedMap {

        public List<Entry> entry = new ArrayList<Entry>();

    }


    public static class Entry {
        @XmlAttribute(name = "valtype")
        public String key;
        @XmlAttribute(name = "value")
        public String value;

    }

    @Override
    public Map<String, String> unmarshal(AdaptedMap adaptedMap) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        for(Entry entry : adaptedMap.entry) {
            map.put(entry.key, entry.value);
        }
        return map;
    }

    //use List to implement Map's feature,like (List students)!
    @Override
    public AdaptedMap marshal(Map<String, String> map) throws Exception {
        AdaptedMap adaptedMap = new AdaptedMap();
        for(Map.Entry<String, String> mapEntry : map.entrySet()) {
            Entry entry = new Entry();
            entry.key = mapEntry.getKey();
            entry.value = mapEntry.getValue();
            adaptedMap.entry.add(entry);
        }
        return adaptedMap;
    }
}
