package com.tt.ly.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by Seven on 17/5/14.
 */
public class FileUtils {

    public Boolean write(String fname, String fcontent,String fpath){
        try {
            File file = new File(fpath);

            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fcontent);
            bw.close();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String readFile(String filePath){
        BufferedReader br;
        String response;

        try {
            if(!new File(filePath).exists()){
                return null;
            }
            StringBuffer output = new StringBuffer();
            FileReader reader = new FileReader(filePath);
            br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            response = output.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        path.delete();
    }
}