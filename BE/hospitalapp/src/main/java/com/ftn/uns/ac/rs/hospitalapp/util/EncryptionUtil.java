package com.ftn.uns.ac.rs.hospitalapp.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtil {

	public static byte[] decrypt(FinalMessage message, PublicKey alisa_public, PrivateKey bob_private) {

		try {
			byte[] symetric_key = decrypt(message.getEncrypted_sym_key(), bob_private);

			byte[] hcm_bytes = decrypt(message.getHash_and_compress(), new String(symetric_key));

			ByteArrayInputStream bais = new ByteArrayInputStream(hcm_bytes);
			GZIPInputStream gzipIn = new GZIPInputStream(bais);
			ObjectInputStream objectIn = new ObjectInputStream(gzipIn);
			
			@SuppressWarnings("unchecked")
			HashMap<String, byte[]> hcm = (HashMap<String, byte[]>) objectIn.readObject();
			objectIn.close();

			byte[] hash = decrypt(hcm.get("hash"), alisa_public);

			// CHECK HASH
			MessageDigest mda = MessageDigest.getInstance("SHA-512", "BC");

			byte[] hash_check = mda.digest(hcm.get("compress"));

			if (Arrays.equals(hash, hash_check)) {
				return hcm.get("compress");
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static <T> T decompress(byte[] data) {
		try {
			ByteArrayInputStream bais2 = new ByteArrayInputStream(data);
			GZIPInputStream gzipIn2 = new GZIPInputStream(bais2);
			ObjectInputStream objectIn2 = new ObjectInputStream(gzipIn2);
			
			@SuppressWarnings("unchecked")
			T return_data = (T) objectIn2.readObject();
			
			objectIn2.close();

			return return_data;
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] decrypt(byte[] data, String myKey) {
		try {
			MessageDigest sha = null;
			byte[] key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

	public static KeyPair generateKeys() {
		try {
			// Generator para kljuceva
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

			// Za kreiranje kljuceva neophodno je definisati generator pseudoslucajnih
			// brojeva
			// Ovaj generator mora biti bezbedan (nije jednostavno predvideti koje brojeve
			// ce RNG generisati)
			// U ovom primeru se koristi generator zasnovan na SHA1 algoritmu, gde je SUN
			// provajder
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

			// inicijalizacija generatora, 2048 bitni kljuc
			keyGen.initialize(2048, random);

			// generise par kljuceva koji se sastoji od javnog i privatnog kljuca
			return keyGen.generateKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> byte[] compress(T data) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			GZIPOutputStream gzipOut = new GZIPOutputStream(baos);
			ObjectOutputStream objectOut = new ObjectOutputStream(gzipOut);

			objectOut.writeObject(data);
			objectOut.close();

			byte[] compressed_data = baos.toByteArray();

			return compressed_data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static FinalMessage encrypt(PublicKey his_public, PrivateKey my_private, byte[] compressed_data,
			String symetric_key) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		try {

			byte[] compressed_message = compressed_data;

			MessageDigest mda = MessageDigest.getInstance("SHA-512", "BC");

			byte[] hash = mda.digest(compressed_message);

			byte[] encrypted_hash = encrypt(hash, my_private);

			HashMap<String, byte[]> hCM = new HashMap<>();

			hCM.put("compress", compressed_message);
			hCM.put("hash", encrypted_hash);

			ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
			GZIPOutputStream gzipOut2 = new GZIPOutputStream(baos2);
			ObjectOutputStream objectOut2 = new ObjectOutputStream(gzipOut2);

			objectOut2.writeObject(hCM);
			objectOut2.close();

			byte[] hcm_bytes = baos2.toByteArray();

			byte[] hcm_symetric_message = cipherEncrypt(hcm_bytes, symetric_key);

			byte[] encrypted_sym_key = encrypt(symetric_key.getBytes(), his_public);

			FinalMessage f = new FinalMessage();
			f.setEncrypted_sym_key(encrypted_sym_key);
			f.setHash_and_compress(hcm_symetric_message);

			return f;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] decrypt(byte[] cipherText, PrivateKey key) {
		try {
			Cipher rsaCipherDec = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");

			// inicijalizacija za desifrovanje
			rsaCipherDec.init(Cipher.DECRYPT_MODE, key);

			// desifrovanje
			byte[] plainText = rsaCipherDec.doFinal(cipherText);
			return plainText;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decrypt(byte[] cipherText, PublicKey key) {
		try {
			Cipher rsaCipherDec = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");

			// inicijalizacija za desifrovanje
			rsaCipherDec.init(Cipher.DECRYPT_MODE, key);

			// desifrovanje
			byte[] plainText = rsaCipherDec.doFinal(cipherText);
			return plainText;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] cipherEncrypt(byte[] data, String myKey) {
		try {
			MessageDigest sha = null;
			byte[] key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static byte[] encrypt(byte[] encrypted, PublicKey key) {
		try {
			Cipher rsaCipherEnc = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");

			rsaCipherEnc.init(Cipher.ENCRYPT_MODE, key);

			byte[] cipherText = rsaCipherEnc.doFinal(encrypted);
			return cipherText;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] encrypt(byte[] hash, PrivateKey key) {
		try {
			Cipher rsaCipherEnc = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");

			rsaCipherEnc.init(Cipher.ENCRYPT_MODE, key);

			byte[] cipherText = rsaCipherEnc.doFinal(hash);
			return cipherText;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
