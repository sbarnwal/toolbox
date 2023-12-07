package org.tool;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HmacUtils {

    String generateHmac256(String message, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] bytes = hmac("HmacSHA256", key, message.getBytes());
        //return new String(bytes);
        return bytesToHex(bytes);
    }

    byte[] hmac(String algorithm, byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(message);
    }

    String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        String valueToDigest = "{\n" +
                "    \"eventAttributes\": {\n" +
                "        \"operationalState\": \"disable\",\n" +
                "        \"name\": \"MSISDN\"\n" +
                "    },\n" +
                "    \"eventTime\": \"2023-07-20T22:51:15+05:30\",\n" +
                "    \"eventType\": \"TERMINATE_LINE\",\n" +
                "    \"event\": \"TERMINATE_LINE\",\n" +
                "    \"applicationId\": null,\n" +
                "    \"transactionId\": \"698696d1-c13a-4a4f-9d6d-40dff852148b\",\n" +
                "    \"id\": {\n" +
                "        \"type\": \"MSISDN\",\n" +
                "        \"value\": \"447883272618\"\n" +
                "    }\n" +
                "}";
        byte[] key = "f31fe879d0c4fa69f5ae27e59db3a45c5c5b944be7d37749c546971ee3b100cd".getBytes();

        HmacUtils hm = new HmacUtils();
        String messageDigest = hm.generateHmac256(valueToDigest, key);

        System.out.println(messageDigest);
    }
}