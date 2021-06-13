package PackUnpack;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUnpacker
{ 
    /*public static void main(String args[]) throws Exception
    {
        FileUnpacker obj = new FileUnpacker();
    }*/
    
    public FileUnpacker() throws Exception
    {
        /*if(Unpack(src))
        {
            System.out.println("Files unpacked");
        }
        else
        {
            System.out.println("Invalid Packed file");
        }*/
    } 

    public boolean Unpack(String filePath)
    {
        try
        {
            MagicNumber mnObj = new MagicNumber();
            FileInputStream instream = new FileInputStream(filePath);
            File file = new File(filePath);

            int iPackedFileSize = (((int)file.length()) - Sizes.CHECKSUM_SIZE);
            int length = 0;

            byte header[]= new byte[Sizes.HEADER_SIZE];
            byte temp[] = new byte[Sizes.CHECKSUM_SIZE];
            byte buffer[] = new byte[iPackedFileSize];
            
            // Extract checksum from packed file
            instream.read(temp);
            String chk = new String(temp);
            
            // Remove unnecessary spaces
            chk = chk.trim();

            // Convert to integer 
            int checksum = Integer.parseInt(chk);
 
            // Read data from file to compute checksum
            instream.read(buffer);
           
            // Compute checksum of current file data
            int currCheckSum = mnObj.getCheckSum(buffer);

            // Display packed file data
            System.out.println("Existing checksum in file: " + checksum);
            System.out.println("Packed file checksum: " + currCheckSum);
            
            // Close instream to set offset to start of file
            instream.close();

            // Compare both checksum to see if the file is valid
            if(checksum == currCheckSum)
            {
                // Open file again
                instream = new FileInputStream(filePath);
                // And start reading from file headers skipping the checksum header
                instream.read(temp);

                while((length = instream.read(header, 0, Sizes.HEADER_SIZE)) > 0)
                {
                    // Decrypt header before extracting info about file
                    header = mnObj.DecryptData(header, Sizes.HEADER_SIZE);

                    String str = new String(header);
                    // Extract filename and its size from header
                    String ext = str.substring(str.lastIndexOf("/"));
                    ext = ext.substring(1);
                    
                    // Seperate filename and size
                    String[] words = ext.split("\\s");
                    String filename = words[0];
                    int iCurrFileSize = Integer.parseInt(words[1]);
                    
                    byte arr[] = new byte[iCurrFileSize];

                    // Read current file data
                    instream.read(arr, 0, iCurrFileSize);
    
                    // Decrypt the data before writing to file
                    arr = mnObj.DecryptData(arr, iCurrFileSize);

                    System.out.println(filename);

                    FileOutputStream fout = new FileOutputStream(filename);
                    // Write Decrypted data to file
                    fout.write(arr, 0, iCurrFileSize);
                }
                instream.close();
                return true;
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return false;
    }
}