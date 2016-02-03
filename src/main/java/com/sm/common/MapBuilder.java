package com.sm.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Newbody on 2016/1/8.
 */
public class MapBuilder {
    public static <K, V> Map<K, V> create(K k, V v){
        Map<K, V> map = new HashMap<>();
        map.put(k, v);
        return map;
    }
    public static <K, V> Map<K, V> create(K[] ks, V[] vs){
        Map<K, V> map = new HashMap<>();

        for(Integer i = 0; i < ks.length; i++){
            K k = ks[i];
            V v = vs[i];
            map.put(k, v);
        }
        return map;
    }
    public static <K, V> Map<K, V> create(Collection<K> ks, Collection<V> vs){
        Map<K, V> map = new HashMap<>();
        Iterator<K> ki = ks.iterator();
        Iterator<V> vi = vs.iterator();
        while (ki.hasNext()){
            map.put(ki.next(), vi.next());
        }
        return map;
    }
}
