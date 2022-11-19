import ClassicCiphers.AffineCipher;
import ClassicCiphers.CaesarCipher;
import ClassicCiphers.PlayfairCipher;
import ClassicCiphers.VigenereCipher;

public class Main {
    public static void main(String[] args) {
        CaesarCipher caesarCipher = new CaesarCipher(12);
        PlayfairCipher playfairCipher = new PlayfairCipher("key");
        VigenereCipher vigenereCipher = new VigenereCipher("key");
        AffineCipher affineCipher = new AffineCipher(1, 1);

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

        // Affine Cipher
        System.out.println("----Affine Cipher----");
        System.out.println(affineCipher.encrypt(message));
        System.out.println(affineCipher.decrypt(affineCipher.encrypt(message)) + "\n");
    }
}