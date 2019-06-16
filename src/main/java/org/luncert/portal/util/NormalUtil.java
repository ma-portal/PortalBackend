package org.luncert.portal.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class NormalUtil {

    public static String throwableToString(Throwable e) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(out));
        return out.toString();
    }

}