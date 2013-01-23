package um.ppc.servidor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Base64;

/**
 * Realiza las funciones criptográficas a petición del servidor.
 * 
 * @author leandro
 * 
 */
public class ServicioCriptografico {
	private static ServicioCriptografico instancia;
	private X509Certificate cert;
	private PrivateKey privKey;
	private PublicKey pubKey;
	private Store certs;

	private String CLAVE_PRIVADA = "key.pem";
	private String CLAVE_PUBLICA = "pubkey.pem";
	private String CERTIFICADO = "cert.pem";

	@SuppressWarnings("resource")
	private ServicioCriptografico() throws IOException, CertificateEncodingException, NoSuchAlgorithmException, NoSuchProviderException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		// crea el certificado
		setCert((X509Certificate) new PEMReader(new BufferedReader(new FileReader(CERTIFICADO))).readObject());

		// Claves en disco
		setPrivKey((PrivateKey) new PEMReader(new BufferedReader(new FileReader(CLAVE_PRIVADA))).readObject());
		setPubKey((PublicKey) new PEMReader(new BufferedReader(new FileReader(CLAVE_PUBLICA))).readObject());

		// Claves generadas
		// KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
		// keyGen.initialize(1024);
		// KeyPair clavesRSA = keyGen.generateKeyPair();
		// setPrivKey(clavesRSA.getPrivate());
		// setPubKey(clavesRSA.getPublic());

		// Crea la cert store
		List<X509Certificate> certList = new ArrayList<X509Certificate>();
		certList.add(getCert());
		setCertStore(certList);
	}

	private void setPubKey(PublicKey clave) {
		pubKey = clave;
	}

	private PublicKey getPubKey() {
		return pubKey;
	}

	private void setCertStore(List<X509Certificate> certList) throws CertificateEncodingException {
		certs = new JcaCertStore(certList);
	}

	private Store getCertStore() {
		return certs;
	}

	private static ServicioCriptografico get() throws IOException, CertificateEncodingException, NoSuchAlgorithmException, NoSuchProviderException {
		if (instancia == null)
			instancia = new ServicioCriptografico();
		return instancia;
	}

	private X509Certificate getCert() {
		return cert;
	}

	private void setCert(X509Certificate cert) {
		this.cert = cert;
	}

	private PrivateKey getPrivKey() {
		return privKey;
	}

	private void setPrivKey(PrivateKey privKey) {
		this.privKey = privKey;
	}

	public static String firmarPKCS1(String cadena) throws Exception {
		ServicioCriptografico sc = get();
		Signature firmador = Signature.getInstance("SHA1withRSA", "BC");
		firmador.initSign(sc.getPrivKey());
		firmador.update(cadena.getBytes());
		byte[] firma = firmador.sign();

		// Guardar la firma en disco
		// FileOutputStream fos = new FileOutputStream("java-firmapkcs1");
		// fos.write(firma);
		// fos.close();

		firmador.initVerify(sc.getPubKey());
		firmador.update(cadena.getBytes());

		boolean verificacion = firmador.verify(firma);

		if (verificacion)
			System.out.println("  Firma PKCS#1 creada y verificada.");
		else
			System.out.println("  Firma no valida :(");
		return new String(Base64.encode(firma));
	}

	@SuppressWarnings("deprecation")
	public static String firmarPKCS7(String cadena) throws Exception {
		ServicioCriptografico sc = get();

		CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
		gen.addSigner(sc.getPrivKey(), sc.getCert(), CMSSignedDataGenerator.DIGEST_SHA1);
		gen.addCertificates(sc.getCertStore());

		CMSProcessable msg = new CMSProcessableByteArray(cadena.getBytes());
		CMSSignedData sigData = gen.generate(msg, true, "BC");

		return new String(Base64.encode(sigData.getEncoded()));
	}
	//
	// public static String firmarPKCS1bis(String cadena) throws Exception {
	// byte[] cipherText = null;
	// try {
	// ServicioCriptografico sc = get();
	//
	// // Compute digest
	// MessageDigest sha1 = MessageDigest.getInstance("SHA1");
	// byte[] digest = sha1.digest((cadena).getBytes());
	//
	// // Encrypt digest
	// Cipher cipher = Cipher.getInstance("RSA");
	// cipher.init(Cipher.ENCRYPT_MODE, sc.getPrivKey());
	// cipherText = cipher.doFinal(digest);
	//
	// // Guardar la firma en disco
	// FileOutputStream fos = new FileOutputStream("sigfile-pkcs1bis");
	// fos.write(cipherText);
	// fos.close();
	// } catch (Exception e) {
	//
	// }
	// return base64ToString(cipherText);
	// }
	//
	// public static String firmarPKCS7_old(String cadena) throws Exception {
	// ServicioCriptografico sc = get();
	//
	// List<X509Certificate> certList = new ArrayList<X509Certificate>();
	// certList.add(sc.getCert());
	// Store certs = new JcaCertStore(certList);
	//
	// CMSTypedData msg = new CMSProcessableByteArray(cadena.getBytes());
	// CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
	// ContentSigner sha1Signer = new
	// JcaContentSignerBuilder("SHA1withRSA").setProvider("BC").build(sc.getPrivKey());
	//
	// gen.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new
	// JcaDigestCalculatorProviderBuilder().setProvider("BC").build()).build(sha1Signer,
	// sc.getCert()));
	//
	// gen.addCertificates(certs);
	//
	// CMSSignedData sigData = gen.generate(msg, false);
	//
	// return base64ToString(sigData.getEncoded());
	// }
	//
	// @SuppressWarnings("deprecation")
	// public byte[] sign(byte[] data) throws Exception {
	// ServicioCriptografico sc = get();
	// Security.addProvider(new BouncyCastleProvider());
	// CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
	// generator.addSigner(sc.getPrivKey(), (X509Certificate) sc.getCert(),
	// CMSSignedDataGenerator.DIGEST_SHA1);
	// // generator.addCertificatesAndCRLs(getCertStore());
	// CMSProcessable content = new CMSProcessableByteArray(data);
	//
	// CMSSignedData signedData = generator.generate(content, true, "BC");
	// return signedData.getEncoded();
	// }
	//
	// @SuppressWarnings("deprecation")
	// public static String firmarPKCS7_viejo(String cadena) throws Exception {
	// ServicioCriptografico sc = get();
	// byte[] firma = null;
	// try {
	// ArrayList<X509Certificate> certList = new ArrayList<X509Certificate>();
	// certList.add(sc.getCert());
	// CertStore certs = CertStore.getInstance("Collection", new
	// CollectionCertStoreParameters(certList), "BC");
	// CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
	// gen.addCertificatesAndCRLs(certs);
	// gen.addSigner(sc.getPrivKey(), sc.getCert(),
	// CMSSignedGenerator.DIGEST_SHA1);
	// CMSSignedData signedData = gen.generate(new
	// CMSProcessableByteArray(cadena.getBytes()), true, "BC");
	// firma = signedData.getEncoded();
	// } catch (Exception e) {
	// System.out.println("Error signing data : " + e.getMessage());
	// }
	// String firmaBase64 = DatatypeConverter.printBase64Binary(firma);
	// return firmaBase64;
	// }

}
