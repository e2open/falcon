package com.e2open.falcon.framework.helpers;

import java.net.URL;

/**
 * Copyright (c) 2013 E2open, INC.
 * All Rights Reserved
 * <p/>
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF E2open
 * The copyright notice above does not evidence any
 * actual or intended publication of such source code.
 */
public class FileHelper {
    public static String getResourceFilePath(String name) {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        URL url = loader.getResource(name);
        if (url == null) {
            throw new RuntimeException("File does not exist: " + name);
        }
        return url.getFile();
    }

}
