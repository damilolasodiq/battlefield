package com.randomapps.battlefield.util;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GameHelper {

    public static String readFileFromClassPathResource(String fileName) {

        StringBuilder result = new StringBuilder("");

        ClassLoader classLoader = GameHelper.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

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

    public static void promptEnterKey() {
        System.out.println("Press \"ENTER\" to continue...");
        try {
            System.in.read(new byte[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCPUName() {
        String[] transformerNames = new String[]{"Megatron", "Bumblebee", "Optimus", "Jazz", "Ratchet", "Ironhide", "Arcee", "Sideswipe", "Hatchet"};
        int rnd = new Random().nextInt(transformerNames.length);
        return transformerNames[rnd];
    }
}
