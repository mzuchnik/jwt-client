package pl.mzuchnik.client.jwt.secret;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class SecretUtil {

    public static RSAPublicKey getPublicKey(File file) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----","")
                .replaceAll(System.lineSeparator(),"")
                .replace("-----END PUBLIC KEY-----","");

        byte[] publicKeyBytes = Base64.decodeBase64(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public static RSAPrivateKey getPrivateKey(File file) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----","")
                .replaceAll(System.lineSeparator(),"")
                .replace("-----END PRIVATE KEY-----","");

        byte[] privateKeyBytes = Base64.decodeBase64(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

}
