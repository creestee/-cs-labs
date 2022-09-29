# Intro to Cryptography. Classical ciphers

## Course: Cryptography & Security

### FAF - 202

### Author: Cristian Ionel

----

## Theory

**Cryptography** is the technique which is used for doing secure communication between two parties in the public environment where unauthorized users and malicious attackers are present. In cryptography there are two processes i.e. encryption and decryption performed at sender and receiver end respectively. **Encryption** is the processes where a simple multimedia data is combined with some additional data (known as key) and converted into unreadable encoded format known as **Cipher**. **Decryption** is the reverse method as that of encryption where the same or different additional data (key) is used to decode the cipher and it is converted in to the real multimedia data.

## Objectives

1. Get familiar with the basics of cryptography and classical ciphers.

2. Implement 4 types of the classical ciphers:
    - Caesar Cipher with one key used for substitution
    - Caesar Cipher with one key used for substitution, and a permutation of the alphabet
    - Vigenere Cipher
    - Playfair Cipher
    - Affine Cipher

## Implementation description

For this laboratory work I've used Java SDK 11. Code snippets, which are mainly the encryption/decryption methods, are listed below every implemented algorithm. Every algorithm is created as a single class, but actually I could made an Interface that would be eventually implemented by those classes with the methods of encrypt and decrypt, therefore overriding them when implementing the algorithms.

</br>

### Affine Cipher

**The Affine Cipher**, (like a shift cipher), is an example of a substitution cipher. In encryption using a substitution cipher, each time a given letter occurs in the plaintext, it always is replaced by the same ciphertext letter. The method used for this replacement in affine encryption can be viewed as a generalization of the method used for encryption using a shift cipher. Shift ciphers are a particular type of affine cipher.

```java
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
```

```java
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
```

</br>

### The Caesar Cipher

**The Caesar Cipher** is one of the simplest and most widely known encryption techniques. It is a type of substitution cipher in which each letter in the plaintext is replaced by a letter some fixed number of positions down the alphabet.

```java
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
```

```java
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
```

</br>

### Playfair Cipher

**The Playfair Cipher** is a manual symmetric encryption technique and was the first literal digram substitution cipher.

```java
    public String encrypt(String text) {

        StringBuilder result = new StringBuilder();

        List<String> dividedMessagesList = divideMessage(text);

        for (String dividedMessage : dividedMessagesList) {
            if (dividedMessage.length() == 2) {
                String encryptedDividedMessage = encryptTwoCharacters(dividedMessage.charAt(0), dividedMessage.charAt(1));
                result.append(encryptedDividedMessage);
            } else {
                result.append(dividedMessage);
            }
        }
        return result.toString();
    }
```

```java
    public String decrypt(String text) {
        StringBuilder result = new StringBuilder();
        List<String> messagesList = divideMessage(text);
        for (String dividedMessage : messagesList) {
            if (dividedMessage.length() == 2) {
                String decryptedDividedMessage = decryptTwoCharacters(dividedMessage.charAt(0), dividedMessage.charAt(1));
                result.append(decryptedDividedMessage);
            } else {
                result.append(dividedMessage);
            }
        }

        return result.toString();
    }
```

</br>

### Vigenere Cipher

**The Vigenère Cipher** is a method of encrypting alphabetic text by using a series of interwoven Caesar ciphers, based on the letters of a keyword. It employs a form of polyalphabetic substitution.

```java
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
```

```java
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
```

</br>

## Conclusions

At this laboratory got familiar with claccical ciphers, their types and how they work. Since classical ciphers are ciphers developed prior to the invention of the computer, they made an intro to the modern days. It's amazing to see how the evolution took place in this domain.

Firstly, I want to mention that I implemented the laboratory by myself and didn't clone from somewhere else c:

During the implementation of this laboratory, I struggled the most with the Playfair Cipher. On the other hand, Caesar Cipher was the easiest to implement.