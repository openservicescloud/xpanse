/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.orchestrator.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;

/**
 * IconProcessorUtil.
 */
public class IconProcessorUtil {

    private static final int MAX_SIZE = 200 * 1024;
    private static final int MAX_WIDTH = 40;
    private static final int MAX_HEIGHT = 40;

    private static String processImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        InputStream inputStream = url.openStream();
        BufferedImage image = ImageIO.read(inputStream);
        int width = image.getWidth();
        int height = image.getHeight();
        int size = getImageSizeInBytes(image);
        if (width <= MAX_WIDTH && height <= MAX_HEIGHT && size <= MAX_SIZE) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, "png", out);
            return Base64.encodeBase64String(out.toByteArray());
        } else {
            return null;
        }

    }

    private static int getImageSizeInBytes(BufferedImage image) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        out.flush();
        int size = out.size();
        out.close();
        return size;
    }

    /**
     * This method get the base64 icon.
     *
     * @param imageUrl The parameter value of the icon.
     */
    public static String getIcon(String imageUrl) {
        if (imageUrl.startsWith("data:image/png;base64,")) {
            return imageUrl;
        } else {
            try {
                String base64 = processImage(imageUrl);
                if (StringUtils.isBlank(base64)) {
                    return "Icon size does not match.";
                } else {
                    return "data:image/png;base64," + base64;
                }
            } catch (IOException e) {
                return "Invalid icon parameter.";
            }
        }
    }
}
