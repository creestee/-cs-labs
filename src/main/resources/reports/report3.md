# Topic: Symmetric Ciphers. Stream Ciphers. Block Ciphers

## Course: Cryptography & Security

### FAF - 202

### Author: Cristian Ionel

----

## Overview
&ensp;&ensp;&ensp; Symmetric Cryptography deals with the encryption of plain text when having only one encryption key which needs to remain private. Based on the way the plain text is processed/encrypted there are 2 types of ciphers:
- Stream ciphers:
    - The encryption is done one byte at a time.
    - Stream ciphers use confusion to hide the plain text.
    - Make use of substitution techniques to modify the plain text.
    - The implementation is fairly complex.
    - The execution is fast.
- Block ciphers:
    - The encryption is done one block of plain text at a time.
    - Block ciphers use confusion and diffusion to hide the plain text.
    - Make use of transposition techniques to modify the plain text.
    - The implementation is simpler relative to the stream ciphers.
    - The execution is slow compared to the stream ciphers.

&ensp;&ensp;&ensp; Some examples of stream ciphers are the following:
- Grain: ...
- HC-256: ...
- PANAMA: ...
- Rabbit: ...
- Rivest Cipher (RC4): It uses 64 or 128-bit long keys. It is used in TLS/SSL and IEEE 802.11 WLAN.
- Salsa20: ...
- Software-optimized Encryption Algorithm (SEAL): ...
- Scream: ...

&ensp;&ensp;&ensp; The block ciphers may differ in the block size which is a parameter that might be implementation specific. Here are some examples of such ciphers:
- 3DES
- Advanced Encryption Standard (AES): A cipher with 128-bit block length which uses 128, 192 or 256-bit symmetric key.
- Blowfish: ...
- Data Encryption Standard (DES): A 56-bit symmetric key cipher.
- Serpent: ...
- Twofish: A standard that uses Feistel networks. It uses blocks of 128 bits with key sizes from 128-bit to 256-bit.


## Objectives:
1. Get familiar with the symmetric cryptography, stream and block ciphers.

2. Implement an example of a stream cipher.

3. Implement an example of a block cipher.

4. The implementation should, ideally follow the abstraction/contract/interface used in the previous laboratory work.

5. Please use packages/directories to logically split the files that you will have.

6. As in the previous task, please use a client class or test classes to showcase the execution of your programs.

## Implementation description

For Stream Cipher part I've studied and implemented RC4 algorithm. It uses 256 bytes of memory for the state array, S[0] through S[255], k bytes of memory for the key, key[0] through key[k−1], and integer variables, i, j, and K. Block Cipher is actually based on RC6-Lite algorithm. RC6 is a parameterized algorithm where the block size, the key size, and the number of rounds are variable. The upper limit on the key size is 2040 bits.

### Stream Cipher (RC4)

**Stream Cipher** is a symmetric key cipher where plaintext digits are combined with a pseudorandom cipher digit stream (keystream). In a stream cipher, each plaintext digit is encrypted one at a time with the corresponding digit of the keystream, to give a digit of the ciphertext stream. Since encryption of each digit is dependent on the current state of the cipher, it is also known as state cipher. In practice, a digit is typically a bit and the combining operation is an exclusive-or (XOR).
**RC4** generates a pseudorandom stream of bits (a keystream). As with any stream cipher, these can be used for encryption by combining it with the plaintext using bitwise exclusive or; decryption is performed the same way (since exclusive or with given data is an involution). This is similar to the one-time pad, except that generated pseudorandom bits, rather than a prepared stream, are used. To generate the keystream, the cipher makes use of a secret internal state which consists of two parts:
- A permutation of all 256 possible bytes (denoted "S" below).
- Two 8-bit index-pointers (denoted "i" and "j").
  
The permutation is initialized with a variable-length key, typically between 40 and 2048 bits, using the key-scheduling algorithm (KSA). Once this has been completed, the stream of bits is generated using the pseudo-random generation algorithm (PRGA).

```java
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
```


### Block Cipher (RC6)

In cryptography, a **block cipher** is a deterministic algorithm operating on fixed-length groups of bits, called blocks. They are specified elementary components in the design of many cryptographic protocols and are widely used to encrypt large amounts of data, including in data exchange protocols. It uses blocks as an unvarying transformation. **RC6** (Rivest cipher 6) is a symmetric key block cipher derived from RC5. RC6 proper has a block size of 128 bits and supports key sizes of 128, 192, and 256 bits up to 2040-bits, but, like RC5, it may be parameterised to support a wide variety of word-lengths, key sizes, and number of rounds. RC6 is very similar to RC5 in structure, using data-dependent rotations, modular addition, and XOR operations; in fact, RC6 could be viewed as interweaving two parallel RC5 encryption processes, although RC6 does use an extra multiplication operation not present in RC5 in order to make the rotation dependent on every bit in a word, and not just the least significant few bits.

```java
  public class BlockCipher {

    private int w = 32;
    private int r = 20;
    private int Pw = 0xB7E15163;
    private int Qw = 0x9E3779b9;
    private int[] S = new int[r * 2 + 4];
    private byte[] output;
    private int counter = 0;
    private int plainTextLength;
  
    private int rotateLeft(int n, int x){
      return ((n << x) | (n >>> (w - x)));
    }
  
    private int rotateRight(int n, int x){
      return ((n >>> x) | (n << (w - x)));
    }
  
    private byte[] convertToHex(int regA,int regB, int regC, int regD){
      int[] data = new int[4];
      byte[] text = new byte[w / 2];
      data[0] = regA;
      data[1] = regB;
      data[2] = regC;
      data[3] = regD;
  
      for(int i = 0;i < text.length;i++){
        text[i] = (byte)((data[i/4] >>> (i%4)*8) & 0xff);
      }
  
      return text;
    }
  
    private void mergeArrays(byte[] array){
      for (int i = 0; i < array.length; i++){
        output[counter] = array[i];
        counter++;
      }
    }
  
    private byte[] fillBufferZeroes(byte[] plainText){
      int length = 16 - plainText.length % 16;
      byte[] block = new byte[plainText.length + length];
      for (int i = 0; i < plainText.length; i++){
        block[i] = plainText[i];
      }
      for(int i = plainText.length; i < plainText.length + length; i++){
        block[i] = 0;
      }
      return block;
    }
  
    private byte[] clearPadding(byte[] cipherText){
      byte[] answer = new byte[getBounds(cipherText)];
      for(int i = 0; i < cipherText.length; i++){
        if(cipherText[i] == 0) break;
        answer[i] = cipherText[i];
      }
  
      return answer;
    }
  
    private int getBounds(byte[] cipherText){
      for(int i = 0; i < cipherText.length; i++){
        if(cipherText[i] == 0){
          return i;
        }
      }
      return cipherText.length;
    }
  
    private byte[] encryptBlock(byte[] plainText){
  
  
      int regA, regB, regC, regD;
      int index = 0, temp1, temp2, swap;
  
      regA = ((plainText[index++] & 0xff) | (plainText[index++] & 0xff) << 8| (plainText[index++] & 0xff) << 16| (plainText[index++] & 0xff)<<24);
      regB = ((plainText[index++] & 0xff) | (plainText[index++] & 0xff) << 8| (plainText[index++] & 0xff) << 16| (plainText[index++] & 0xff)<<24);
      regC = ((plainText[index++] & 0xff) | (plainText[index++] & 0xff) << 8| (plainText[index++] & 0xff) << 16| (plainText[index++] & 0xff)<<24);
      regD = ((plainText[index++] & 0xff) | (plainText[index++] & 0xff) << 8| (plainText[index++] & 0xff) << 16| (plainText[index++] & 0xff)<<24);
  
      regB = regB + S[0];
      regD = regD + S[1];
  
      for(int i = 1; i <= r ; i++){
        temp1 = rotateLeft(regB * (regB * 2 + 1), 5);
        temp2 = rotateLeft(regD * (regD * 2 + 1), 5);
        regA = (rotateLeft(regA ^ temp1, temp2)) + S[i * 2];
        regC = (rotateLeft(regC ^ temp2, temp1)) + S[i * 2 + 1];
  
        swap = regA;
        regA = regB;
        regB = regC;
        regC = regD;
        regD = swap;
      }
  
      regA = regA + S[r * 2 + 2];
      regC = regC + S[r * 2 + 3];
  
      return convertToHex(regA, regB, regC, regD);
    }
  
    private byte[] decryptBlock(byte[] cipherText){
  
      int regA, regB, regC, regD;
      int index = 0, temp1, temp2, swap;
  
      regA = ((cipherText[index++] & 0xff) | (cipherText[index++] & 0xff) << 8| (cipherText[index++] & 0xff) << 16| (cipherText[index++] & 0xff)<<24);
      regB = ((cipherText[index++] & 0xff) | (cipherText[index++] & 0xff) << 8| (cipherText[index++] & 0xff) << 16| (cipherText[index++] & 0xff)<<24);
      regC = ((cipherText[index++] & 0xff) | (cipherText[index++] & 0xff) << 8| (cipherText[index++] & 0xff) << 16| (cipherText[index++] & 0xff)<<24);
      regD = ((cipherText[index++] & 0xff) | (cipherText[index++] & 0xff) << 8| (cipherText[index++] & 0xff) << 16| (cipherText[index++] & 0xff)<<24);
  
  
      regC = regC - S[r * 2 + 3];
      regA = regA - S[r * 2 + 2];
  
      for(int i = r; i >= 1 ; i--){
        swap = regD;
        regD = regC;
        regC = regB;
        regB = regA;
        regA = swap;
  
        temp2 = rotateLeft(regD * (regD * 2 + 1), 5);
        temp1 = rotateLeft(regB * (regB * 2 + 1), 5);
        regC =  rotateRight(regC - S[i * 2 + 1], temp1) ^ temp2;
        regA =  rotateRight(regA -  + S[i * 2], temp2) ^ temp1;
      }
  
      regD = regD - S[1];
      regB = regB - S[0];
      return convertToHex(regA, regB, regC, regD);
    }
  
    public byte[] encrypt(byte[] plainText, byte[] userKey){
      int blocks_number = plainText.length / 16 + 1;
      int block_counter = 0;
      plainTextLength = plainText.length;
      output = new byte[16*blocks_number];
      keyShedule(userKey);
      for(int i = 0; i < blocks_number; i++){
        if(blocks_number == i + 1){
          mergeArrays(encryptBlock(fillBufferZeroes(Arrays.copyOfRange(plainText, block_counter , plainText.length))));
          break;
        }
        mergeArrays(encryptBlock(Arrays.copyOfRange(plainText, block_counter ,block_counter+16)));
        block_counter += 16;
      }
      counter = 0;
      return output;
    }
  
    public byte[] decrypt(byte[] cipherText, byte[] userKey){
      int blocks_number = cipherText.length / 16 + 1;
      int block_counter = 0;
      output = new byte[16*blocks_number];
      keyShedule(userKey);
  
      for(int i = 0; i < blocks_number; i++){
        if(blocks_number == i + 1){
          mergeArrays(decryptBlock(fillBufferZeroes(Arrays.copyOfRange(cipherText, block_counter ,cipherText.length))));
          break;
        }
        mergeArrays(decryptBlock(Arrays.copyOfRange(cipherText, block_counter ,block_counter+16)));
        block_counter += 16;
      }
      counter = 0;
  
      return clearPadding(output);
    }
  
    private void keyShedule(byte[] key){
      int bytes = w / 8;
      int c = key.length / bytes;
      int[] L = new int[c];
      int index = 0;
  
      for(int i = 0; i < c; i++){
        L[i] = ((key[index++]) & 0xff | (key[index++] & 0xff) << 8 | (key[index++] & 0xff) << 16 | (key[index++] & 0xff) << 24);
      }
      S[0] = Pw;
  
      for(int i = 1; i <= 2*r+3; i++){
        S[i] = S[i-1] + Qw;
      }
  
      int A = 0, B = 0, i = 0,j = 0;
      int v = 3 * Math.max(c, 2*r+4);
  
      for(int k = 1;k <= v; k++){
        A = S[i] = rotateLeft(S[i] + A + B, 3);
        B = L[j] = rotateLeft(L[j] + A + B, A+B);
        i = (i + 1) % (2 * r + 4);
        j = (j + 1) % c;
      }
    }
}
```


## Conclusions

At this laboratory I got familiar with stream and block ciphers, differences between them and their flaws. It was really funny (in a pain way) to play with Block Cipher (RC6), but with Google and StackOverFlow help I think I've managed somehow. 

So, basically I've understood that block ciphers have slower processing than stream ciphers, require more resources, but in some cases are better because they are more secure, but it also depends on its properties. 