package classic;

public class CaesarCipher {


    private final int letterCount = 26;
    private final int key;

    public CaesarCipher(int key) {
        this.key = key;
    }

    public String encrypt(String text) {
        StringBuilder cha = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            if (Character.isUpperCase(currentChar)) {
                char ch = (char) (((int) currentChar +
                        key - 'A') % letterCount + 'A');
                cha.append(ch);
            } else if (Character.isLowerCase(currentChar)) {
                char ch = (char) (((int) currentChar +
                        key - 'a') % letterCount + 'a');
                cha.append(ch);
            } else {
                cha.append(currentChar);
            }
        }
        return cha.toString();
    }

    public String decrypt(String text) {
        StringBuilder result = new StringBuilder();

        for (char currentChar : text.toCharArray()) {
            if (Character.isUpperCase(currentChar)) {
                char ch = (char) (((int) currentChar -
                        key - 'A' + letterCount) % letterCount + 'A');
                result.append(ch);
            } else if (Character.isLowerCase(currentChar)) {
                char ch = (char) (((int) currentChar -
                        key - 'a' + letterCount) % letterCount + 'a');
                result.append(ch);
            } else {
                result.append(currentChar);
            }
        }
        return result.toString();
    }

}