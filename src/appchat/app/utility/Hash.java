package appchat.app.utility;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmConstraints;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class Hash {
    private static Random random = new Random();
    private static Cipher cipher;
    private static String secretkey = "javaappchatkey00";

    // SHA-1 mã hóa mật khẩu
    public static String randomString(int length){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String string = new String();
        for (int i = 0; i < length; i++){
            string += chars.charAt(random.nextInt(chars.length()));
        }
        return string;
    }

    public static String generateSaltedSHA1(String passwordString, String salt){
        String generatedPassword = null;
        byte[] bytesalt = salt.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(bytesalt);
            byte[] bytes = md.digest(passwordString.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    // AES256 mã hóa bảo mật thông tin
    // mã hóa
    public static byte[] getEncryptionAES256(String message) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec skeySpec = new SecretKeySpec(secretkey.getBytes(), "AES");
        cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] byteEncrypted = cipher.doFinal(message.getBytes());
        String encrypted =  Base64.getEncoder().encodeToString(byteEncrypted);
        System.out.println(encrypted);
        return byteEncrypted;
    }

    //giải mã
    public static String getDecryptionAES256(byte[] byteencrypted) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        SecretKeySpec skeySpec = new SecretKeySpec(secretkey.getBytes(), "AES");
        cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] byteDecrypted = cipher.doFinal(byteencrypted);
        String decrypted = new String(byteDecrypted);
        return decrypted;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        String message = "Hello";
        getEncryptionAES256(message);
        System.out.println(getDecryptionAES256(getEncryptionAES256(message)));
    }
}
