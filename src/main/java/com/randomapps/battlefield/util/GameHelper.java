package com.randomapps.battlefield.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class GameHelper {

    public static String readFileFromClassPathResource(String fileName) {

        StringBuilder result = new StringBuilder("");

        try(InputStream in = GameHelper.class.getClassLoader().getResourceAsStream("readme.txt")){
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            reader.lines().forEach(line -> result.append(line).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }

    public static void clearConsole(){
        final String os = System.getProperty("os.name");
        if (os.contains("Windows"))
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        else
            try {
                Runtime.getRuntime().exec("clear");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static boolean isWindowsOS() {
        final String os = System.getProperty("os.name");
        return os.contains("Windows");
    }
    public static void promptEnterKey() {
        // Scanner s = new Scanner(System.in);
        System.out.println("Press \"ENTER\" to continue...");
        //s.nextLine();
    }

    public static void pressAnyKeyToContinue() {
        System.out.println("Press \"ENTER\" key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }
    public static String getCPUName() {
        String[] transformerNames = new String[]{"Megatron", "Bumblebee", "Optimus", "Jazz", "Ratchet", "Ironhide", "Arcee", "Sideswipe", "Hatchet"};
        int rnd = new Random().nextInt(transformerNames.length);
        return transformerNames[rnd];
    }
}
