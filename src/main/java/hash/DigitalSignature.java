package hash;

import org.apache.commons.codec.digest.DigestUtils;
import assymetric.RSA;

import java.util.HashMap;
import java.util.Map;

public class DigitalSignature {

    RSA rsa = new RSA(1024);

    public Map<String, String> signMessage(String message) {
        Map<String, String> map = new HashMap<>();
        String hashedMessage = DigestUtils.sha256Hex(message);
        map.put(message, rsa.encrypt(hashedMessage));
        return map;
    }

    public boolean verifySignature(String message, Map<String, String> map) {
        try {
            String hashedMessage = DigestUtils.sha256Hex(message);
            String hashedMessageAfterDecryption = rsa.decrypt(map.get(message));
            return hashedMessage.equals(hashedMessageAfterDecryption);
        }
        catch (Exception e) {
            return false;
        }
    }
}
