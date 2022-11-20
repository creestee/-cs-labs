package symmetric;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
    // random generator
    private final static SecureRandom random = new SecureRandom();

    private BigInteger p; // prime number
    private BigInteger q; // prime number
    private BigInteger n; // p*q
    private BigInteger e; // public key where gcd(e,phi)=1
    private BigInteger d; // private key where ed = 1 mod phi

    public RSA(int N) {
        p = BigInteger.probablePrime(N, random);
        q = BigInteger.probablePrime(N, random);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        n = p.multiply(q);
        e = BigInteger.probablePrime(N / 2, random);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);
    }

    public BigInteger getPublicKey() {
        return e;
    }

    public BigInteger getPrivateKey() {
        return d;
    }

    public String encrypt(String message) {
        BigInteger n = new BigInteger("769750914680484372200078422578788743792190453917708306205411");
        BigInteger e = new BigInteger("823738732813999");
        BigInteger numL, encL;
        StringBuilder result = new StringBuilder();

        for (char letter : message.toCharArray()) {
            numL = BigInteger.valueOf(letter);
            encL = numL.modPow(e, n);
            result.append(encL).append(" ");
        }
        return result.toString();
    }

    public String decrypt(String encryptedM) {
        BigInteger numL, decInt;
        String decL;
        BigInteger n = new BigInteger("769750914680484372200078422578788743792190453917708306205411");
        BigInteger d = new BigInteger("353343052159423642183327550893401946314922128579120163105999");
        StringBuilder result = new StringBuilder();

        String[] letters = encryptedM.split(" ");
        for (String letter : letters) {
            numL = new BigInteger(letter); // each letter to big integer format
            decInt = numL.modPow(d, n); // decoded letter(BigInteger format)
            decL = new String(decInt.toByteArray()); // decoded letter(string format)
            result.append(decL);
        }
        return result.toString();
    }
}