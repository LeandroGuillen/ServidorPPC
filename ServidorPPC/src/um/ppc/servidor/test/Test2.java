package um.ppc.servidor.test;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

public class Test2 {

	public static void main(String[] args) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		byte[] input = "hiho".getBytes();
		Cipher cipher = Cipher.getInstance("RSA", "BC");
		// RSA/ECB/OAEPPadding
		KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger("12345678", 16), new BigInteger("11", 16));
		RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(new BigInteger("12345678", 16), new BigInteger("12345678", 16));

		RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
		RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);

		cipher.init(Cipher.ENCRYPT_MODE, pubKey);

		byte[] cipherText = cipher.doFinal(input);
		System.out.println("cipher: " + new String(cipherText));

		cipher.init(Cipher.DECRYPT_MODE, privKey);
		byte[] plainText = cipher.doFinal(cipherText);
		System.out.println("plain : " + new String(plainText));
		
		
		System.out.println("printBase64Binary(cipherText) : " + DatatypeConverter.printBase64Binary(cipherText));
		System.out.println("printBase64Binary(plainText) : " + DatatypeConverter.printBase64Binary(plainText));

	}
	// Converts an array of bytes into a string.
	// printBase64Binary

	// Converts the string argument into an array of bytes.
	// parseBase64Binary
}
