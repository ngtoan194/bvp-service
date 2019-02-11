/*
 * *******************************************************************
 * Copyright (c) 2018 Isofh.com to present.
 * All rights reserved.
 *
 * Author: tuanld
 * ******************************************************************
 *
 */

package com.isofh.service.utils;

import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Cac ham thao tac voi file
 */
public class FileUtils {

    /**
     * Kiem tra xem file co ton tai khong
     *
     * @param path
     * @return
     */
    public static boolean existed(String path) {
        File tmpDir = new File(path);
        return tmpDir.exists();
    }

    /**
     * Write Stream to file
     *
     * @param uploadedInputStream stream nhan dc
     * @param fileLocation        vi tri muon luu file
     * @throws IOException loi xay ra trong qua trinh luu file
     */
    public static File writeToFile(InputStream uploadedInputStream,
                                   String fileLocation) throws IOException {
        File file = createFile(fileLocation);
        try (OutputStream out = new FileOutputStream(file)) {
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            return file;
        } catch (IOException e) {
            throw e;
        }

    }

    /**
     * Tao moi 1 file neu parent is not existed
     *
     * @param path duong dan dan file
     * @return File duoc tao ra
     * @throws IOException Exception neu co loi khi tao file
     */
    public static File createFile(String path) throws IOException {
        File file = new File(path);
        if(!file.getParentFile().exists()) {
            boolean createDir = file.getParentFile().mkdirs();
            if (!createDir)
                return null;
        }
        boolean createFile = file.createNewFile();
        if (!createFile)
            return null;
        return file;
    }

    public static String getFileContent(String fileName) {
        String result = "";
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
