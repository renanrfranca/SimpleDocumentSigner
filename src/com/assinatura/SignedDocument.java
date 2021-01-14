package com.assinatura;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SignedDocument {
    private final String fileName = "DocSigned.txt";

    private String documentName = "";
    private String documentBase64Data = "";

    private String certificateBase64Data = "";
    private String certificateCommonName = "";

    public void writeFile() {
        String fileContents = this.generateContents();

        this.createFile();
        this.writeFileContents(fileContents);
    }

    private boolean createFile() {
        File signedDoccumentFile = new File(this.fileName);
        try {
            return signedDoccumentFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void writeFileContents(String contents) {
        try {
            FileWriter writer = new FileWriter(this.fileName);
            writer.write(contents);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateContents() {
        String contents = "-----BEGIN DOCSIGNED-----\n"
                .concat("doc:" + this.documentName + "\n")
                .concat("alg:RSA\n")
                .concat("hash:SHA1\n")
                .concat("assinante:" + this.certificateCommonName + "\n\n")
                .concat("-----BEGIN DOC-----\n")
                .concat(this.documentBase64Data + "\n")
                .concat("-----END DOC-----\n")
                .concat("-----BEGIN SIGNATURE-----\n")
                .concat(this.certificateBase64Data + "\n")
                .concat("-----END SIGNATURE-----\n")
                .concat("-----END DOCSIGNED-----\n");

        return contents;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public void setCertificateCommonName(String certificateCommonName) {
        this.certificateCommonName = certificateCommonName;
    }

    public void setDocumentBase64Data(String documentBase64Data) {
        this.documentBase64Data = documentBase64Data;
    }

    public void setCertificateBase64Data(String certificateBase64Data) {
        this.certificateBase64Data = certificateBase64Data;
    }
}
