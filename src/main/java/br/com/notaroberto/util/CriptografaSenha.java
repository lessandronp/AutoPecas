package br.com.notaroberto.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CriptografaSenha {

    private static MessageDigest md = null;

    /**
     * Metodo estatico para a geracao do algoritmo de criptografia.
     */
    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Criptografa a senha.
     *
     * @param senha
     *            String A senha normal.
     * @return String A senha criptografada.
     */
    public static String criptografar(String senha) {
        if (md != null) {
            return new String(hexCodes(md.digest(senha.getBytes())));
        }
        return null;
    }

    private static char[] hexCodes(byte[] text) {
        char[] hexOutput = new char[text.length * 2];
        String hexString;

        for (int i = 0; i < text.length; i++) {
            hexString = "00" + Integer.toHexString(text[i]);
            hexString.toUpperCase().getChars(hexString.length() - 2,
                    hexString.length(), hexOutput, i * 2);
        }
        return hexOutput;
    }
}
