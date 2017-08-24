package demo02_transform_encoding;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 转换中文编码
 * Created by fansy on 2017/8/24.
 */
public class TransformEncoding {
    public static void main(String[] args) {
        String parentStr = "data/文本分类语料库";
        File dataParent = new File(parentStr);

        List<File> files = getFiles(parentStr);
        String output =null ;
        for(File file:files){
            transform(file,"data/all/"+file.getName());
        }
    }

    public static List<File> getFiles(String fileStr){
        File file = new File(fileStr);
        List<File> files = new ArrayList<File>();
        if(file.isDirectory()){
            for(File f:file.listFiles()){
                if(f.isDirectory()){
                    files.addAll(getFiles(f.getAbsolutePath()));
                }else{
                    files.add(f);
                }
            }
        }else{
            files.add(file);
        }
        return files;
    }

    public static boolean transform(String input,String output){
        File file = new File(input);
        return transform(file,output);
    }
    public static boolean transform(File file,String output){
//        File file = new File(input);
        BufferedReader reader = null;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");
            reader = new BufferedReader(isr);
            String line = null;
            long i=0;
            // 一次读入一行，直到读入null为文件结束
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(new File(output)),"UTF-8");
            while ((line = reader.readLine()) != null) {
                i++;
                out.write(line+"\n");
                if(i % 1000 == 0){
                    out.flush();
                }
            }
            isr.close();
            reader.close();
            out.close();
            return true;
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
        return false;
    }
}
