package com.eric.mooncoin.services;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * BTC Markets request interceptor, handle authentication stuff
 */
public class BTCMarketsRequestInterceptor implements ClientHttpRequestInterceptor, BTCMarketsConstants {
    @Value("btc.markets.api.key")
    private String apiKey="NA";
    @Value("btc.markets.private.key")
    private String privateKey="NA";

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        String timestamp = Long.toString(System.currentTimeMillis());
        // create the string that needs to be signed
        String uri = request.getURI().toString().replace(BASEURL, "");
        String stringToSign = buildStringToSign(uri, null, new String(body), timestamp);

        // build signature to be included in the http header
        String signature = signRequest(privateKey, stringToSign);

        HttpHeaders headers = request.getHeaders();

        headers.add("Accept", "*/*");
        headers.add("Accept-Charset", ENCODING);
        headers.add("Content-Type", "application/json");

        // Add signature, timestamp and apiKey to the http header
        headers.add(SIGNATURE_HEADER, signature);
        headers.add(APIKEY_HEADER, apiKey);
        headers.add(TIMESTAMP_HEADER, timestamp);

        return execution.execute(request, body);
    }

    private static String buildStringToSign(String uri, String queryString,
                                            String postData, String timestamp) {
        // queryString must be sorted key=value& pairs
        String stringToSign = uri + "\n";
        if (queryString != null) {
            stringToSign += queryString + "\n";
        }
        stringToSign += timestamp + "\n";
        stringToSign += postData;
        return stringToSign;
    }

    private static String signRequest(String secret, String data) {
        String signature = "";
        try {
            Mac mac = Mac.getInstance(ALGORITHM);
            SecretKeySpec secret_spec = new SecretKeySpec(Base64.decodeBase64(secret), ALGORITHM);
            mac.init(secret_spec);
            signature = Base64.encodeBase64String(mac.doFinal(data.getBytes()));
        } catch (InvalidKeyException e) {
            System.out.println(e);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
        return signature;
    }
}
