package com.assinatura;

import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Informe como argumentos o nome do documento e o nome do arquivo PKCS#12");
            return;
        }

        String filePath = args[0];
        String fileName = Paths.get(filePath).getFileName().toString();
        KeyFile keyFile = new KeyFile(args[1], "Seguranca");

        byte[] fileContents = new DocumentReader(filePath).getContent();
        String base64FileContents = Base64.getEncoder().encodeToString(fileContents);
        String base64Signature = generateFileSignature(fileContents, keyFile.getPk());

        SignedDocument signedDocument = new SignedDocument();
        signedDocument.setDocumentName(fileName);
        signedDocument.setBase64Signature(base64Signature);
        signedDocument.setCertificateCommonName(keyFile.getCn());
        signedDocument.setBase64FileData(base64FileContents);
        signedDocument.writeFile();
    }

    private static String generateFileSignature(byte[] content, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(privateKey);
            signature.update(content);
            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}