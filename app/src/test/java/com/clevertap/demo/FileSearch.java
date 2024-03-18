package com.clevertap.demo;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.io.BufferedReader;

public class FileSearch {
    public static void main(String[] args) {
        // Testing only
        find();
    }

    public static boolean find() {
        String file = "";

        Scanner in = null;
        try {
            in = new Scanner(new FileReader(file));
            while(in.hasNextLine()) {
                System.out.println(in.nextLine());
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            try { in.close() ; } catch(Exception e) { /* ignore */ }
        }
        return true;
    }
}

