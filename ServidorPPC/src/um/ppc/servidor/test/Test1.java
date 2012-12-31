package um.ppc.servidor.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.encodings.OAEPEncoding;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;

public class Test1 {

	public static void main(String[] args) throws Exception {
		test3();
	}

	public static void test1() throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		byte[] input = "hi".getBytes();
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
	}

	public static void test2() throws Exception {
		
	}

	/*
	 * Ejemplo de encriptacion y desencriptacion con RSA de un fichero de texto.
	 */
	public static void test3() throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
		Cipher cipher = Cipher.getInstance("RSA", "BC");

		kpg.initialize(1024);
		KeyPair keyPair = kpg.generateKeyPair();
		PrivateKey privKey = keyPair.getPrivate();
		PublicKey pubKey = keyPair.getPublic();

		cipher.init(Cipher.ENCRYPT_MODE, pubKey);

		String cleartextFile = "cleartext.txt";
		String ciphertextFile = "ciphertextRSA.txt";
		String cleartextAgainFile = "cleartextAgainRSA.txt";

		FileInputStream fis = new FileInputStream(cleartextFile);
		FileOutputStream fos = new FileOutputStream(ciphertextFile);
		CipherOutputStream cos = new CipherOutputStream(fos, cipher);

		byte[] block = new byte[32];
		int i;
		while ((i = fis.read(block)) != -1) {
			cos.write(block, 0, i);
		}
		cos.close();

		cipher.init(Cipher.DECRYPT_MODE, privKey);

		fis = new FileInputStream(ciphertextFile);
		CipherInputStream cis = new CipherInputStream(fis, cipher);
		fos = new FileOutputStream(cleartextAgainFile);

		while ((i = cis.read(block)) != -1) {
			fos.write(block, 0, i);
		}
		fos.close();

	}
	
	/*
	 * Ejemplo de como firmar con SHA1 y RSA
	 */
	public static void test4() throws Exception{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
		Cipher cipher = Cipher.getInstance("RSA", "BC");

		kpg.initialize(1024);
		KeyPair keyPair = kpg.generateKeyPair();
		PrivateKey privKey = keyPair.getPrivate();
		PublicKey pubKey = keyPair.getPublic();
		
		
		Signature signer = Signature.getInstance("SHA1withRSA");
		signer.initSign(privKey);
		signer.update("hola".getBytes());
		byte[] signature = signer.sign();
	}
}
