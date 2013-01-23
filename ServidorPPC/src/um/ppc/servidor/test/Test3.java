package um.ppc.servidor.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSSignedGenerator;
import org.bouncycastle.openssl.PEMReader;


public class Test3 {

	public static void main(String[] args) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		BufferedReader br = null, br2 = null;
		PrivateKey privKey;
		X509Certificate cert;
		String keyPath = "key.pem";
	
		br = new BufferedReader(new FileReader(keyPath));
		privKey = (PrivateKey) new PEMReader(br).readObject();

		String certPath = "cert.pem";
		br2 = new BufferedReader(new FileReader(certPath));
		cert = (X509Certificate) new PEMReader(br2).readObject();

		String msj="hola";
		System.out.println(msj);
		
//		firmar(privKey, msj);
		signData(privKey, cert, msj.getBytes());
	}

	public static void firmar(PrivateKey clavePrivada, String cadena) throws Exception {
		Signature firmador = Signature.getInstance("SHA1withRSA", "BC");

		firmador.initSign(clavePrivada);
		firmador.update(cadena.getBytes());
		byte[] firma = firmador.sign();
		String firmaBase64 = DatatypeConverter.printBase64Binary(firma);
		System.out.println(firmaBase64);
	}
	
	 /**
     * Method that signes the given data using the algorithm specified in the init method.
     * 
     * @param signKey, the key used to sign the data
     * @param signCert the certificate
     * @param data
     * @return the signed data or null if signature failed
     */
    public static byte[] signData(PrivateKey signKey, X509Certificate signCert, byte[] data){
        byte[] retdata = null;
        try{
            ArrayList certList = new ArrayList();
            certList.add(signCert);
            CertStore certs = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");           
            CMSSignedDataGenerator    gen = new CMSSignedDataGenerator();
            gen.addCertificatesAndCRLs(certs);
            gen.addSigner(signKey, signCert, CMSSignedGenerator.DIGEST_SHA1);        
            CMSSignedData           signedData = gen.generate(new CMSProcessableByteArray(data), true, "BC");
            retdata = signedData.getEncoded();
        } catch(Exception e){
            System.out.println("Error signing data : "+ e.getMessage());
        }
        return retdata;
    }   
}
