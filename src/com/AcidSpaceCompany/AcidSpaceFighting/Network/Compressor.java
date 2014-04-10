package com.AcidSpaceCompany.AcidSpaceFighting.Network;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Compressor {

    public static String decompress(byte[] a) {
        try {
            GZIPInputStream is = new GZIPInputStream(new ByteArrayInputStream(a));
            byte[] b2 = new byte[1000];
            is.read(b2);
            String res = "";
            for (byte aB2 : b2) {
                if (aB2==0) break;
                res += (char) aB2;
            }
            return res;
        } catch (IOException e) {
            System.err.println("[Compressor] Failed to decompress byte[]");
            return null;
        }
    }

    public static String decompress(String s) {
        return s;
        /*byte[] b = new byte[s.length()];
        for (int i = 0; i < s.length(); i++) {
            b[i] = (byte) s.charAt(i);
        }
        return decompress(b);*/
    }

    public static String compress(String s) {
        return s;
        /*try {
            ByteArrayOutputStream zippedDataOS = new ByteArrayOutputStream();
            GZIPOutputStream os = new GZIPOutputStream(zippedDataOS);
            os.write(s.getBytes("UTF-8"));
            os.close();
            byte[] c = zippedDataOS.toByteArray();

            String res = "";
            for (byte c2 : c) {
                res += (char) c2;
            }
            return res;
        } catch (IOException e) {
            System.err.println("[Compressor] Failed to compress string");
            return null;
        }*/
    }

}
