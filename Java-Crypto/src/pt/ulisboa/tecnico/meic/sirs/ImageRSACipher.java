package pt.ulisboa.tecnico.meic.sirs;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;

import javax.crypto.spec.*;
import java.security.spec.*;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Encrypts an image with the AES algorithm in multiple modes, with a given, appropriate AES key
 */
public class ImageRSACipher {

    public static void main(String[] args) throws IOException, GeneralSecurityException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {

        if(args.length != 3) {
            System.err.println("This program encrypts an image file with RSA.");
            System.err.println("Usage: ImageRSACipher [inputFile.png] [PubKeyFile] [outputFile.png]");
            return;
        }

        final String inputFile = args[0];
        final String keyFile = args[1];
        final String outputFile = args[2];


        BufferedImage image = ImageIO.read(new File(inputFile));
        byte[] imageBytes = ImageMixer.imageToByteArray(image);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, RSAKeyGenerator.read(keyFile));
        byte[] outBytes = cipher.doFinal(imageBytes);

        BufferedImage outputImage = ImageMixer.getImageFromArray(outBytes, image.getWidth(), image.getHeight());
        ImageMixer.writeImageToFile(outputImage, outputFile);
    }

}
