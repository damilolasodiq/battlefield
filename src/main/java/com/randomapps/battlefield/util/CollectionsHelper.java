package com.randomapps.battlefield.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionsHelper {

    private CollectionsHelper(){}

    public static <T> List<T> createListWithNewObjects(Class<T> t, int x) {
       List<T> tList = new ArrayList<>();
       for(int i =0; i <x; i++){
           try {
               tList.add(t.newInstance());
           } catch (InstantiationException | IllegalAccessException e) {
               e.printStackTrace();
           }
       }
       return tList;
    }
}
