# Topic: Hash functions and Digital Signatures

## Course: Cryptography & Security

### FAF - 202

### Author: Cristian Ionel

----

## Overview
&ensp;&ensp;&ensp; Hashing is a technique used to compute a new representation of an existing value, message or any piece of text. The new representation is also commonly called a digest of the initial text, and it is a one way function meaning that it should be impossible to retrieve the initial content from the digest.

&ensp;&ensp;&ensp; Such a technique has the following usages:
* Offering confidentiality when storing passwords,
* Checking for integrity for some downloaded files or content,
* Creation of digital signatures, which provides integrity and non-repudiation.

&ensp;&ensp;&ensp; In order to create digital signatures, the initial message or text needs to be hashed to get the digest. After that, the digest is to be encrypted using a public key encryption cipher. Having this, the obtained digital signature can be decrypted with the public key and the hash can be compared with an additional hash computed from the received message to check the integrity of it.


## Examples
1. Argon2
2. BCrypt
3. MD5 (Deprecated due to collisions)
4. RipeMD
5. SHA256 (And other variations of SHA)
6. Whirlpool


## Objectives:
1. Get familiar with the hashing techniques/algorithms.
2. Use an appropriate hashing algorithms to store passwords in a local DB.
    1. You can use already implemented algorithms from libraries provided for your language.
    2. The DB choose is up to you, but it can be something simple, like an in memory one.
3. Use an asymmetric cipher to implement a digital signature process for a user message.
    1. Take the user input message.
    2. Preprocess the message, if needed.
    3. Get a digest of it via hashing.
    4. Encrypt it with the chosen cipher.
    5. Perform a digital signature check by comparing the hash of the message with the decrypted one.

## Implementation description

For this Laboratory Work I've studied about SHA256 hashing algorithm, which is one of the most used algorithms of this kind.
For first part, I chose an in-memory database - H2, which is an open-source lightweight Java database. 
It can be embedded in Java applications or run in the client-server mode. 
Mainly, H2 database can be configured to run as in-memory database, as in my case. 

Entity `UserCredentials` is a persistent class that is mapped to a database "user_credentials" table. So it has some
columns, its no-arg constructor, some getters/setters. 

```java
   @Entity
   @Table(name = "user_credentials")
   public class UserCredentials {
   
       @Id
       @Column(name = "id")
       private int id;
   
       @Column(name = "username")
       private String username;
   
       @Column(name = "password")
       private String password;
```

Also, I've created a Hibernate configuration file `hibernate.cfg.xml` that contains information about the database and mapping file.

`HibernateUtil` is a utility class that helps to bootstrap hibernate SessionFactory, so it configures singleton
`SessionFactory` and use it throughout the application.

```java
public static SessionFactory getSessionFactory() { ... }
```

The class `AppDb` is used to connect the HSQLDB database and persist `UserCredentials` object in the database table.

For the hashing, I've used SHA256 algorithm from `DigestUtils` class, that has method `sha256Hex` which is used for hashing strings.

```java
import org.apache.commons.codec.digest.DigestUtils;

...
        
appDb.SaveIntoDb(new UserCredentials(0, "User1", DigestUtils.sha256Hex("password-to-be-hashed")));
```

---

Regarding digital signature process, I created a simple class called `DigitalSignature`, that has a sign method (after signing, every message is mapped to its encrypted digest) :

```java
    public Map<String, String> signMessage(String message) {
        Map<String, String> map = new HashMap<>();
        String hashedMessage = DigestUtils.sha256Hex(message);
        map.put(message, rsa.encrypt(hashedMessage));
        return map;
    }
```

and a verifying signature method : 

```java
    public boolean verifySignature(String message, Map<String, String> map) {
        try {
            String hashedMessage = DigestUtils.sha256Hex(message);
            String hashedMessageAfterDecryption = rsa.decrypt(map.get(message));
            return hashedMessage.equals(hashedMessageAfterDecryption);
        }
        catch (Exception e) {
            return false;
        }
```

So the implementation example is relatively easy to understand 

```java
    Map<String, String> mapSigned = digitalSignature.signMessage(messageToBeSigned);

    if (digitalSignature.verifySignature(messageToBeSigned, mapSigned))
        System.out.println("Signature is valid");
    else
        System.out.println("Signature is not valid");
```

## Conclusions

While doing this Laboratory Work, I've understood better why hashing is that valuable nowadays. Hashing is important because it offers a method for retrieving data that's secure and efficient. It's also quicker than most traditional sorting algorithms, which makes it more efficient for retrieving data. The main properties of a good hash are :
- It’s extremely fast to turn an input into a short code.
- That code has very little bias, as in, any tiny change completely randomizes it.
- It’s virtually impossible to turn the hash back into the input, without simply trying every possible input.

So hashes are a fast, compact way to verify that a received message is intact. 

Hash functions and public-key cryptography are at the core of digital signature systems, which are now applied to a wide range of use cases. If properly implemented, digital signatures can increase security, ensure integrity, and facilitate the authentication of all kinds of digital data.