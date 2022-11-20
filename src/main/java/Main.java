import classic.CaesarCipher;
import classic.PlayfairCipher;
import classic.VigenereCipher;
import hash.DigitalSignature;
import hash.db.AppDb;
import hash.entity.UserCredentials;
import org.apache.commons.codec.digest.DigestUtils;
import assymetric.RSA;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        CaesarCipher caesarCipher = new CaesarCipher(12);
        PlayfairCipher playfairCipher = new PlayfairCipher("key");
        VigenereCipher vigenereCipher = new VigenereCipher("key");

        String message = "Message to be encrypted";

        // Caesar Cipher
        System.out.println("----Caesar Cipher----");
        System.out.println(caesarCipher.encrypt(message));
        System.out.println(caesarCipher.decrypt(caesarCipher.encrypt(message)) + "\n");

        // Playfair Cipher
        System.out.println("----Playfair Cipher----");
        System.out.println(playfairCipher.encrypt(message));
        System.out.println(playfairCipher.decrypt(playfairCipher.encrypt(message)) + "\n");

        // Vigenere Cipher
        System.out.println("----Vigenere Cipher----");
        System.out.println(vigenereCipher.encrypt(message));
        System.out.println(vigenereCipher.decrypt(vigenereCipher.encrypt(message)) + "\n");

        // RSA (Symmetric Cipher)
        RSA rsa = new RSA(1024);
        System.out.println("----RSA Encryption----");
        System.out.println(rsa.encrypt(message));
        System.out.println(rsa.decrypt(rsa.encrypt(message)) + "\n");

        System.out.println("-----BEGIN PUBLIC KEY-----");
        System.out.println(rsa.getPublicKey());
        System.out.println("-----END PUBLIC KEY-----");
        System.out.println("-----BEGIN PRIVATE KEY-----");
        System.out.println(rsa.getPrivateKey());
        System.out.println("-----END PRIVATE KEY-----" + "\n");

        // Hashing a password and storing it into a Database
        AppDb appDb = new AppDb();
        appDb.SaveIntoDb(new UserCredentials(0, "User1", DigestUtils.sha256Hex("password-to-be-hashed")));

        System.out.println();

        // Digital Signature
        DigitalSignature digitalSignature = new DigitalSignature();
        String messageToBeSigned = "A very important message, written by Luffy";
        System.out.println("["+ messageToBeSigned + "]" + "  will be digitally signed...");

        Map<String, String> mapSigned = digitalSignature.signMessage(messageToBeSigned);

        if (digitalSignature.verifySignature(messageToBeSigned, mapSigned))
            System.out.println("Signature is valid");
        else
            System.out.println("Signature is not valid");
    }
}