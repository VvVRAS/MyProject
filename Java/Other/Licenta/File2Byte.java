package Licenta;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class File2Byte {
    /*private File file = new File(/home/wras/Documents/Java/Licenta/file.txt);
    public void firstmethod(){
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
        buf.read(bytes, 0, bytes.length);
        buf.close();
    }*/

   /* public void secondmethod() {
        byte bytes[] = new byte[(int) file.length()];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        DataInputStream dis = new DataInputStream(bis);
        dis.readFully(bytes);
    }*/

   /* public void thirdmethod(){
        byte bytes[] = FileUtils.readFileToByteArray(photoFile)
    }*/

    public byte[] fourthmethod() throws IOException {
        File file = new File("filepath");
        //init array with file length
        byte[] bytesArray = new byte[(int) file.length()]; 

        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();
        return bytesArray;
    }
}
