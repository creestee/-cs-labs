package ClassicCiphers;

import java.lang.*;

public class AffineCipher{

    private int a;
    private int b;

    public AffineCipher(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public String encrypt(String input) {
        StringBuilder str = new StringBuilder();
        for (int in = 0; in < input.length(); in++) {
            char get = input.charAt(in);
            if (Character.isLetter(get)) {
                get = (char) ((a * (get + 'A') + b) % 26 + 'A');
            }
            str.append(get);
        }
        return str.toString();
    }

    public String decrypt(String input) {
        StringBuilder str = new StringBuilder();
        int x = 0;
        int inverse = 0;
        while(true){
            inverse = a * x % 26;
            if (inverse == 1)
                break;
            x++;
        }
        for (int in = 0; in < input.length(); in++) {
            char get = input.charAt(in);
            if (Character.isLetter(get)) {
                get = (char)(x * ((get + 'A') - b) % 26 + 'A');
            }
            str.append(get);
        }
        return str.toString();
    }
}