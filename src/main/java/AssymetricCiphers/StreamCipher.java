package AssymetricCiphers;

import java.util.Arrays;

public class StreamCipher {

    private static final int SBOX_LENGTH = 256;
    private byte[] key = new byte[SBOX_LENGTH - 1];
    private int[] sbox = new int[SBOX_LENGTH];

    public StreamCipher() {
        reset();
    }

    private void reset() {
        Arrays.fill(key, (byte) 0);
        Arrays.fill(sbox, 0);
    }

    public byte[] encrypt(String message, String key) {
        reset();
        setKey(key);
        byte[] crypt = crypt(message.getBytes());
        reset();
        return crypt;
    }

    public String decrypt(byte[] message, String key) {
        reset();
        setKey(key);
        byte[] msg = crypt(message);
        reset();
        return new String(msg);
    }

    public byte[] crypt(final byte[] msg) {
        sbox = initSBox(key);
        byte[] code = new byte[msg.length];
        int i = 0;
        int j = 0;
        for (int n = 0; n < msg.length; n++) {
            i = (i + 1) % SBOX_LENGTH;
            j = (j + sbox[i]) % SBOX_LENGTH;
            swap(i, j, sbox);
            int rand = sbox[(sbox[i] + sbox[j]) % SBOX_LENGTH];
            code[n] = (byte) (rand ^ msg[n]);
        }
        return code;
    }

    private int[] initSBox(byte[] key) {
        int[] sbox = new int[SBOX_LENGTH];
        int j = 0;

        for (int i = 0; i < SBOX_LENGTH; i++) {
            sbox[i] = i;
        }

        for (int i = 0; i < SBOX_LENGTH; i++) {
            j = (j + sbox[i] + (key[i % key.length]) & 0xFF) % SBOX_LENGTH;
            swap(i, j, sbox);
        }
        return sbox;
    }

    private void swap(int i, int j, int[] sbox) {
        int temp = sbox[i];
        sbox[i] = sbox[j];
        sbox[j] = temp;
    }

    public void setKey(String key) {
        this.key = key.getBytes();
    }

}