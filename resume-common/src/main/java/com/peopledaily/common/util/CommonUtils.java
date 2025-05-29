package com.peopledaily.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

public class CommonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String AES_KEY = "your-aes-key-here"; // 实际应用中应从配置中读取
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    // JSON处理
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON序列化失败", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON反序列化失败", e);
        }
    }

    // 加密解密
    public static String encrypt(String data) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(AES_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("加密失败", e);
        }
    }

    public static String decrypt(String encryptedData) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(AES_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("解密失败", e);
        }
    }

    // 日期处理
    public static String formatDate(Date date, String pattern) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    // 字符串处理
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String md5(String input) {
        return DigestUtils.md5DigestAsHex(input.getBytes());
    }

    // 验证工具
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isNullOrEmpty(String str) {
        return !StringUtils.hasText(str);
    }

    // 文件处理
    public static String getFileExtension(String filename) {
        if (isNullOrEmpty(filename)) return "";
        int lastDotIndex = filename.lastIndexOf('.');
        return lastDotIndex == -1 ? "" : filename.substring(lastDotIndex + 1).toLowerCase();
    }

    // 敏感信息脱敏
    public static String maskPhone(String phone) {
        if (isNullOrEmpty(phone)) return phone;
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static String maskEmail(String email) {
        if (isNullOrEmpty(email)) return email;
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) return email;
        int length = atIndex;
        String masked = email.substring(0, 1) + "****" + email.substring(atIndex);
        return masked;
    }

    // 计算相似度（简单实现，实际应用中可能需要更复杂的算法）
    public static double calculateSimilarity(String str1, String str2) {
        if (str1 == null || str2 == null) return 0.0;
        int maxLength = Math.max(str1.length(), str2.length());
        if (maxLength == 0) return 1.0;
        return 1.0 - (double) levenshteinDistance(str1, str2) / maxLength;
    }

    private static int levenshteinDistance(String str1, String str2) {
        int[][] distance = new int[str1.length() + 1][str2.length() + 1];
        for (int i = 0; i <= str1.length(); i++) distance[i][0] = i;
        for (int j = 0; j <= str2.length(); j++) distance[0][j] = j;

        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                distance[i][j] = Math.min(
                    Math.min(distance[i - 1][j] + 1, distance[i][j - 1] + 1),
                    distance[i - 1][j - 1] + (str1.charAt(i - 1) == str2.charAt(j - 1) ? 0 : 1)
                );
            }
        }
        return distance[str1.length()][str2.length()];
    }
} 