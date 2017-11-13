package com.randomapps.battlefield.util;

import com.randomapps.battlefield.barrack.armory.Pistol;

import java.util.ArrayList;
import java.util.List;

public class CollectionsHelper {

    public static <T> List<T> createListWithNewObjects(Class<T> t, int x) {
       List<T> tList = new ArrayList<>();
       for(int i =0; i <x; i++){
           try {
               tList.add(t.newInstance());
           } catch (InstantiationException e) {
               e.printStackTrace();
           } catch (IllegalAccessException e) {
               e.printStackTrace();
           }
       }
       return tList;
    }
}
