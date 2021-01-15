package com.assinatura;

import java.nio.file.Files;
import java.nio.file.Paths;

public class DocumentReader {
    private String path = "";

    public DocumentReader(String path) {
        this.path = path;
    }

    public byte[] getContent() {
        try {
            return Files.readAllBytes(Paths.get(this.path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
