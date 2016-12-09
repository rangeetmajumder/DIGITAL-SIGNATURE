import java.io.*;
import java.security.*;
import java.security.spec.*;

class verifysignature
{
    public static void main(String[] args) 
    {
         if (args.length != 3) 
        {
            System.out.println("Enter in the following manner: VerSig's-->names of ( publickeyfile signaturefile datafile)");
        }
        else 
        try
        {
            FileInputStream keyfis = new FileInputStream(args[0]);
            byte[] encKey = new byte[keyfis.available()];  
            keyfis.read(encKey);
            keyfis.close();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
            KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
            FileInputStream sigfis = new FileInputStream(args[1]);
            byte[] sigToVerify = new byte[sigfis.available()]; 
            sigfis.read(sigToVerify );
            sigfis.close();
            Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
            sig.initVerify(pubKey);
            FileInputStream datafis = new FileInputStream(args[2]);
            BufferedInputStream bufin = new BufferedInputStream(datafis);
            byte[] byteupdater = new byte[1024];
            int len;
            while (bufin.available() != 0) 
            {
                len = bufin.read(byteupdater);
                sig.update(byteupdater, 0, len);
            }
            bufin.close();
            boolean checksig = sig.verify(sigToVerify);
            System.out.println("The signature of the given file verifies: " + checksig);
        }
        catch (Exception exceptn)
        {
            System.err.println("Caught exception " + exceptn.toString());
        }
 
    }
 
}