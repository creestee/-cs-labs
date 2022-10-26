package org.example;

public class Main {
    public static void main(String[] args) {
        StreamCipher streamCipher = new StreamCipher();
        BlockCipher blockCipher = new BlockCipher();

        String key = "10101";
        String message = "message-to-be-encrypted";

        byte[] encryptedStream = streamCipher.encrypt(message, key);
        String decryptedStream = streamCipher.decrypt(encryptedStream, key);

        System.out.println("Decrypted message with StreamCipher is : " + decryptedStream);

        byte[] encryptedBlock = blockCipher.encrypt(message.getBytes(), key.getBytes());
        byte[] decryptedBlock = blockCipher.decrypt(encryptedBlock, key.getBytes());

        System.out.println("Decrypted message with BlockCipher is : " + new String(decryptedBlock));
    }
}