package pl.mzuchnik.client.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.mzuchnik.client.jwt.secret.SecretUtil;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

@Component
public class JwtGenerator {

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    private String pathToPublicKey = "src/main/resources/keys/public_key.pem";
    private String pathToPrivateKey = "src/main/resources/keys/private_key.pkcs8";

    public JwtGenerator() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        publicKey = SecretUtil.getPublicKey(new File(pathToPublicKey));
        privateKey = SecretUtil.getPrivateKey(new File(pathToPrivateKey));
    }

    public String generateJWT(HashMap<String,String> claims){
        JWTCreator.Builder builder = JWT.create();
        claims.forEach(builder::withClaim);
        return builder.sign(Algorithm.RSA256(publicKey,privateKey));
    }
    public String generateJWT(){
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("name","Mateusz");
        builder.withClaim("role","admin");
        return builder.sign(Algorithm.RSA256(publicKey,privateKey));
    }


}
