package org.thiki.kanban.foundation.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by xubt on 7/6/16.
 */
public class FileUtil {

    public static String readFile(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
                sb.append("\r\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return sb.toString();
    }
}
