package PackUnpack;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class FilePacker
{
    FileOutputStream outstream = null;
    String ValidExt[] = {".txt",".c",".java",".cpp", ".png"};
    
    /*public static void main(String args[]) throws Exception
    {
        FilePacker obj = new FilePacker("/root/FilePackerUnpacker/Demo/","combine.txt");
    }*/
    
    public FilePacker(String src, String Dest) throws Exception
    {
        outstream = new FileOutputStream(Dest);

        File[] files = new File(src).listFiles();
        
        System.setProperty("user.dir",src);
        
        traverseAndPackFiles(files);

        // Close the packed file to reset offset to start
        outstream.close();
        
        MagicNumber mnObj = new MagicNumber();

        // Attach magic number to the packed file
        mnObj.attachMagicNumber(Dest);
    }
    
    public void traverseAndPackFiles(File[] files)
    {
        // Traverse all files that are to be packed
        for(File file : files)
        {
            if(!file.isDirectory())
            {
                try
                {
                    String fileName = file.getName();
                    // System.out.println(fileName);

                    // Extract file extension to check if file is valid or not
                    String ext = fileName.substring(fileName.lastIndexOf("."));
                                      
                    List<String> list = Arrays.asList(ValidExt);
                    if(list.contains(ext))
                    {
                        // Pack current file             
                        Pack(file.getAbsolutePath());
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
            }
        }
    }

    public void Pack(String filePath)
    {
        FileInputStream instream = null;
        
        try
        {
            MagicNumber mnObj = new MagicNumber();
            File fobj = new File(filePath);
            
            int iCurrFileSize = (int)fobj.length();

            byte[] buffer = new byte[iCurrFileSize];
            byte header[] = new byte[Sizes.HEADER_SIZE];
            
            // Construct File Header for current file
            String temp = filePath+" "+iCurrFileSize;
            for (int i = temp.length(); i < Sizes.HEADER_SIZE; i++)
                temp += " ";
            
            header = temp.getBytes();
            // Encrypt Header before writing 
            header = mnObj.EncryptData(header, Sizes.HEADER_SIZE);
            instream = new FileInputStream(filePath);

            // Write encrypted Header for current file in packed file
            outstream.write(header, 0, header.length);
            
            // Read current file data
            instream.read(buffer, 0, iCurrFileSize);
  
            // Encrypt the data before writing to file
            buffer = mnObj.EncryptData(buffer, iCurrFileSize);

            // Write Encrypted data to packed file
            outstream.write(buffer, 0, iCurrFileSize);
            
            instream.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

