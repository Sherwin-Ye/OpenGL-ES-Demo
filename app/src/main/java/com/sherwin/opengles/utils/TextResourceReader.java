package com.sherwin.opengles.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Context;
import android.content.res.Resources;

/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/11/15.10:13
 * @desc 读取raw中的资源文件
 */
public class TextResourceReader {
    /**
     * Reads in text from a resource file and returns a String containing the
     * text.
     */
    public static String readTextFileFromResource(Context context,
                                                  int resourceId) {
        StringBuilder body = new StringBuilder();

        try {
            InputStream inputStream =
                    context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String nextLine;

            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not open resource: " + resourceId, e);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resource not found: " + resourceId, nfe);
        }

        return body.toString();
    }
}