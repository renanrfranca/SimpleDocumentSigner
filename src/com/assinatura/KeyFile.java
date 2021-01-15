package com.assinatura;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class KeyFile {
    private String path = "";
    private char[] password;

    private String cn = "";
    private PrivateKey pk = null;

    public KeyFile(String path, String password) {
        this.path = path;
        this.password = password.toCharArray();
        this.readKeyFile();
    }

    private void readKeyFile() {
        try {
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            keystore.load(new FileInputStream(this.path), this.password);
            String alias = keystore.aliases().nextElement();

            if(keystore.getCertificate(alias).getType().equals("X.509")){
                String dn = ((X509Certificate)  keystore.getCertificate(alias)).getSubjectDN().toString();
                this.cn = dn.replaceAll(".*CN=|,.*", "");
                this.pk = (PrivateKey) keystore.getKey(alias, this.password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCn() {
        return this.cn;
    }

    public PrivateKey getPk() {
        return this.pk;
    }
}


