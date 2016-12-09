import java.io.*;
import java.security.*;
class generatesignature
{
    public static void main(String[] args)
    {
        if (args.length != 1) 
        {
            System.out.println("Enter the name Of File To Sign");
        }
        else
        try
        {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom randvariable = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(1024, randvariable);
            KeyPair pairvariable = keyGen.generateKeyPair();
            PrivateKey priv = pairvariable.getPrivate();
            PublicKey pub = pairvariable.getPublic();
            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN"); 
            dsa.initSign(priv);
            FileInputStream fileinputvar = new FileInputStream(args[0]);
            BufferedInputStream bufin = new BufferedInputStream(fileinputvar);
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) 
            {
                len = bufin.read(buffer);
                dsa.update(buffer, 0, len);
            }
            bufin.close();
            byte[] realSig = dsa.sign();
            FileOutputStream sigfos = new FileOutputStream("signature");
            sigfos.write(realSig);
            sigfos.close();
            byte[] key = pub.getEncoded();
            FileOutputStream keyfos = new FileOutputStream("sigpublickey");
            keyfos.write(key);
            keyfos.close();
        } 
        catch (Exception e) 
        {
            System.err.println("Caught exception " + e.toString());
        }
    }
}
