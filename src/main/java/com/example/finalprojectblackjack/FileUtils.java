package com.example.finalprojectblackjack;

import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    public static InputStream loadResource(String path) {
        return FileUtils.class.getResourceAsStream(path);
    }
}
