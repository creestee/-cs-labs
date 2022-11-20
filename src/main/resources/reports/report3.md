# Topic: Asymmetric Ciphers.

## Course: Cryptography & Security

### FAF - 202

### Author: Cristian Ionel

----


## Overview
&ensp;&ensp;&ensp; Asymmetric Cryptography (a.k.a. Public-Key Cryptography)deals with the encryption of plain text when having 2 keys, one being public and the other one private. The keys form a pair and despite being different they are related.

&ensp;&ensp;&ensp; As the name implies, the public key is available to the public but the private one is available only to the authenticated recipients.

&ensp;&ensp;&ensp; A popular use case of the asymmetric encryption is in SSL/TLS certificates along side symmetric encryption mechanisms. It is necessary to use both types of encryption because asymmetric ciphers are computationally expensive, so these are usually used for the communication initiation and key exchange, or sometimes called handshake. The messages after that are encrypted with symmetric ciphers.


## Examples
1. RSA
2. Diffie-Helman
3. ECC
4. El Gamal
5. DSA


## Objectives:
1. Get familiar with the asymmetric cryptography mechanisms.

2. Implement an example of an asymmetric cipher.

3. As in the previous task, please use a client class or test classes to showcase the execution of your programs.

## Implementation description

For this laboratory work I've decided to implement RSA algorithm. I've used `java.math.BigInteger`
class because it is well-suited to implement RSA. BigInteger provides analogues to all of Java's primitive integer operators, and all relevant methods from java. lang. Math. Additionally, BigInteger provides operations for modular arithmetic, GCD calculation, primality testing, prime generation, bit manipulation, and a few other miscellaneous operations.


This constructor is used to set up RSA information
based on bit length chosen by the user. It initializes
public key(`e`) and private key(`d`).

```java
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
```

This method is used to encrypt the message. The encryption is based on the pre-generated RSA public keys(n, e).
```java
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
```

This method is used to decrypt the message. The decryption is based on the pre-generated personal RSA private key(n, d).

```java
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
```

## Conclusions

At this laboratory I got familiar with Asymmetric Ciphers (RSA in my case) and their applicability.
Increased data security is the primary benefit of asymmetric cryptography. It is the most secure 
encryption process because users are never required to reveal or share their private keys, 
thus decreasing the chances of a cybercriminal discovering a user's private key during 
transmission.

The benefits of asymmetric cryptography include:
- The key distribution problem is eliminated because there's no need for exchanging keys.
- Security is increased since the private keys don't ever have to be transmitted or revealed to anyone.
- The use of digital signatures is enabled so that a recipient can verify that a message comes from a particular sender.
- It allows for nonrepudiation so the sender can't deny sending a message.

Disadvantages of asymmetric cryptography include:
- It's a slow process compared to symmetric cryptography. Therefore, it's not appropriate for decrypting bulk messages.
- If an individual loses his private key, he can't decrypt the messages he receives.
- Because public keys aren't authenticated, no one can ensure a public key belongs to the person specified. Consequently, users must verify that their public keys belong to them.
- If a malicious actor identifies a person's private key, the attacker can read that individual's messages.