package com.github.loadup.capability.common.util.crypto;

/*-
 * #%L
 * loadup-common-util
 * %%
 * Copyright (C) 2022 - 2023 loadup_cloud
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * AES加解密工具类
 */
public class AESUtil {

    /**
     * 默认字符集
     */
    public static final String CHARSET_UTF8 = "UTF-8";

    /**
     * AES常量
     */
    private static final String AES = "AES";

    /**
     * 加密<br/>
     * 默认 utf-8字符集
     *
     * @param content 原文
     * @param key     密钥
     * @return 密文
     * @throws CryptoException
     */
    public static String encrypt(final String content, final String key) throws CryptoException {
        return encrypt(content, key, CHARSET_UTF8);
    }

    /**
     * 解密<br/>
     * 默认 utf-8字符集
     *
     * @param content 密文
     * @param key     的密钥
     * @return 原文
     * @throws CryptoException
     */
    public static String decrypt(final String content, final String key) throws CryptoException {
        return decrypt(content, key, CHARSET_UTF8);
    }

    /**
     * 加密<br/>
     *
     * @param content 原文
     * @param key     密钥
     * @param charset 字符集，为null则使用utf-8字符集
     * @return 密文
     * @throws CryptoException
     */
    public static String encrypt(final String content, final String key,
                                 String charset) throws CryptoException {
        try {
            // 获取字符集
            charset = StringUtils.defaultIfBlank(charset, CHARSET_UTF8);

            SecretKeySpec spec = new SecretKeySpec(Base64.decodeBase64(key.getBytes()), AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, spec);
            byte[] byteEnc = cipher.doFinal(getContentBytes(content, charset));
            return new String(Base64.encodeBase64(byteEnc));
        } catch (Exception e) {
            throw new CryptoException("encrypt failed.", e);
        }
    }

    /**
     * 解密服务
     *
     * @param content 密文
     * @param key     的密钥
     * @param charset 字符集，为null则使用utf-8字符集
     * @return 原文
     * @throws CryptoException
     */
    public static String decrypt(final String content, final String key,
                                 String charset) throws CryptoException {

        try {
            // 获取字符集
            charset = StringUtils.defaultIfBlank(charset, CHARSET_UTF8);

            SecretKeySpec spec = new SecretKeySpec(Base64.decodeBase64(key.getBytes()), AES);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, spec);
            byte[] encryptByte = Base64.decodeBase64(content.getBytes());
            byte[] original = cipher.doFinal(encryptByte);
            return new String(original, charset);
        } catch (Exception e) {
            throw new CryptoException("decrypt failed.", e);
        }
    }

    /**
     * 获取指定字符集字节
     *
     * @param content 内容
     * @param charset 字符集
     * @return 字节数组
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content,
                                          String charset) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(charset)) {
            return content.getBytes();
        }

        return content.getBytes(charset);
    }

}
