package PackUnpack;

import java.io.File;
import java.util.Arrays;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MagicNumber
{
    private FileOutputStream outstream;
    private String DestinationFile;

    /*public static void main(String args[]) throws Exception
    {
        MagicNumber obj = new MagicNumber("combine.txt");
    }*/

    // Default constructor
    public MagicNumber()
    {
        this.outstream = null;
        this.DestinationFile = null;
    }
    
    public void attachMagicNumber(String DestinationFile)
    {
        FileInputStream fin = null;
       
        File packedFile = new File(DestinationFile);

        int iPackedFileSize = (int)packedFile.length();
        System.out.println("Packed file size:" + iPackedFileSize);
        byte[] data = new byte[iPackedFileSize];

        try
        {
            fin = new FileInputStream(DestinationFile);
            // Read file data to compute checksum
            fin.read(data);
           
            // Open the packed file to attach checksum header
            outstream = new FileOutputStream(DestinationFile);

            // Get checksum for file contents
            int chk = getCheckSum(data);

            // Convert to string to write in file
            String checksum = String.valueOf(chk);

            // See checksum
            System.out.println("Original Checksum: " + checksum);

            // Construct Header for file checksum
            for(int i = checksum.length(); i < Sizes.CHECKSUM_SIZE; ++i)
            {
                checksum = checksum + " ";
            }

            // Write checksum header in file 
            outstream.write(checksum.getBytes());
            // Then write file contents
            outstream.write(data, 0, iPackedFileSize);

            // Close all used files
            outstream.close();
            fin.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }     
    }

    public byte[] EncryptData(byte bArr[], int iKey)
    {
        for(int i = 0; i < bArr.length; ++i)
        {
            bArr[i] = (byte)(bArr[i] ^ iKey);
        }

        return bArr;
    }

    public byte[] DecryptData(byte bArr[], int iKey)
    {
        return EncryptData(bArr, iKey);
    }

    public int getCheckSum(byte bArr[])
    {
        // Compute checksum for file contents
        int iChecksum = Arrays.hashCode(bArr);

        // Don't return negative checksum
        if(iChecksum < 0)
        {
            iChecksum = iChecksum * -1;
        }
        
        return iChecksum;
    }
}

