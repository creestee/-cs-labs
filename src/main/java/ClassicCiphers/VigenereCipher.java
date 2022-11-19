package ClassicCiphers;

public class VigenereCipher {
    private static final char[][] cipherArray = new char[26][26];
    private static String key;

    static {
        char startChar = 65;
        for (int j = 0; j < 26; j++) {
            for (int i = 0; i < 26; i++) {
                cipherArray[j][i] = startChar + i + j > 90 ? (char)(startChar + i + j - 26) : (char)(startChar + i + j);
            }
        }
    }

    public VigenereCipher(String key) {
        this.key = key;
    }

    private String buildKey (int length) {

        StringBuilder keyStream = new StringBuilder(length);
        String keyString;

        int keysInText = length / key.length();
        keysInText++;
        keyStream.append(key.repeat(Math.max(0, keysInText)));
        keyString = keyStream.substring(0, length);
        return keyString;
    }

    public String encrypt(String text) {
        key = key.toUpperCase();
        text = text.toUpperCase();

        StringBuilder encryptedMessage = new StringBuilder(text.length());
        String keyString = buildKey(text.length());

        char[] keyArray = keyString.toCharArray();
        char[] textArray = text.toCharArray();

        for (int i = 0; i < textArray.length; i++) {
            if (textArray[i] == 32) {
                encryptedMessage.append(' ');
                continue;
            }
            encryptedMessage.append(cipherArray[keyArray[i] - 65][textArray[i] - 65]);
        }
        return encryptedMessage.toString();
    }

    public String decrypt(String text) {
        key = key.toUpperCase();
        text = text.toUpperCase();

        StringBuilder decryptedMessage = new StringBuilder(text.length());
        String keyString = buildKey(text.length());

        char[] keyArray = keyString.toCharArray();
        char[] textArray = text.toCharArray();

        for (int i = 0; i < textArray.length; i++) {
            if (textArray[i] == 32) {
                decryptedMessage.append(' ');
                continue;
            }
            char[] cipherRow = cipherArray[keyArray[i] - 65];
            for (int j = 0; j < cipherRow.length; j++) {
                if (cipherRow[j] == textArray[i]) {
                    decryptedMessage.append((char)(j + 65));
                }
            }
        }

        return decryptedMessage.toString();
    }
}