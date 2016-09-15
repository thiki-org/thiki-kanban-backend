package org.thiki.kanban.foundation.common;

import java.io.*;

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

    /***
     * 覆盖原来的内容；
     *
     * @param filePath 文件的路径
     * @param content  保存的内容；
     * @return
     */
    public static boolean saveFile(String filePath, String content) {
        boolean successful = true;
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(new File(filePath), false);
            fout.write(content.getBytes());
        } catch (FileNotFoundException e1) {
            successful = false;
        } catch (IOException e) {
            successful = false;
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                }
            }
        }
        return successful;
    }

    /**
     * 写入txt文件，可以在原文件内容的基础上追加内容(并判断目录是否存在，不存在则生成目录)
     *
     * @param fileName 写入文件内容
     * @param value    文件名字；
     * @throws IOException
     */
    public static void WriteFile(String fileName, String value) {
        File file = null;
        try {
            file = new File(fileName);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream out = new FileOutputStream(file, true);
            out.write(value.getBytes("utf-8"));
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
