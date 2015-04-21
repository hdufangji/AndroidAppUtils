package com.victor_fun.android_app_utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * please create a java project, then add class to test
 * @author VFang
 *
 */
public class ProjectCoderNumberCalculateUtil
{
    private int lineCount;
    private int fileCount;
    public int getLineCount()
    {
        return lineCount;
    }
    public int getFileCount()
    {
        return fileCount;
    }
 
    public static void main(String[] args) throws IOException
    {
        ProjectCoderNumberCalculateUtil itemCount = new ProjectCoderNumberCalculateUtil();
        //path的值就是你的项目路径 E:\tfs_all_code\N2\Applications\MediaHome\g_16\d_Feature\Android\NeroWifiSync-android\src\com\nero\android\kwiksync
        String path = "E:\\tfs_all_code\\N2\\Applications\\MediaHome\\g_16\\d_Feature\\Android\\NeroWifiSync-android\\src\\com\\nero\\android\\kwiksync";
        itemCount.getItemLineNum(new File(path));
        System.out.println("该项目一共有"+itemCount.getFileCount()+"个java源文件,"+itemCount.getLineCount()+"行代码");
    }
     
    //递归
    public void getItemLineNum(File path) throws IOException{
        if(path.isFile() && path.getName().endsWith(".java")){
            BufferedReader br = new BufferedReader(new FileReader(path));
            fileCount++;
            while(br.readLine()!=null){
                lineCount++;
            }
            System.out.println(path.getName());
            br.close();
        }else if(path.isDirectory()){
            File[] listFiles = path.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
            	getItemLineNum(listFiles[i]);
            }
            
//            for (File file : listFiles)
//            {
//                getItemLineNum(file);
//            }
        }
    }
}
