package com.android.structure.helperclasses;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class CyberSoftSecurityHelper {
    private final String HMAC_SHA256 = "HmacSHA256";
    private final String SECRET_KEY = "96643d5010c848c6a0e7c6f2e2b342eb94c04052923d4d4697972e60a2b208439aed1efae87e4be8b4a1ed8232cc68f0e78dd08e030a4613bd86f01daeebd6996bb7eed245b1461a8a52bec74704e15aa11aba065ff4412bbd1ef9127b5d4e956c32a5b78b3d4a738cbcba64ae3ef13c7d09132df3474e209c4404e5a3c328c9";

    public String sign(HashMap params) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        return sign(buildDataToSign(params), SECRET_KEY);
    }

    private String sign(String data, String secretKey) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
//        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), HMAC_SHA256);
//        Mac mac = Mac.getInstance(HMAC_SHA256);
//        mac.init(secretKeySpec);
//
//        byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
//        return org.apache.commons.codec.binary.Base64.encodeBase64(rawHmac).toString();


        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(data.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return new String(Hex.encodeHex(sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8))));
//
//        byte[] bytes = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
//        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private String buildDataToSign(HashMap params) {
        String[] signedFieldNames = String.valueOf(params.get("signed_field_names")).split(",");
        ArrayList<String> dataToSign = new ArrayList<String>();
        for (String signedFieldName : signedFieldNames) {
            dataToSign.add(signedFieldName + "=" + params.get(signedFieldName));
        }
        return commaSeparate(dataToSign);
    }

    private String commaSeparate(ArrayList<String> dataToSign) {
        StringBuilder csv = new StringBuilder();
        for (Iterator<String> it = dataToSign.iterator(); it.hasNext(); ) {
            csv.append(it.next());
            if (it.hasNext()) {
                csv.append(",");
            }
        }
        return csv.toString();

    }
}