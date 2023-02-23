package com.example.userlogin.adapter

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class HashPassword {
    fun hashPassword(password: String): String? {
        try {
            // Sử dụng thuật toán SHA-256 để băm mật khẩu
            val md: MessageDigest = MessageDigest.getInstance("SHA-256")
            val hashedPassword: ByteArray = md.digest(password.toByteArray(StandardCharsets.UTF_8))

            // Chuyển đổi byte array thành chuỗi hex string
            val sb = StringBuilder()
            for (b in hashedPassword) {
                sb.append(String.format("%02x", b))
            }
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return null
    }

    fun verifyPassword(password: String?, hashedPassword: String): Boolean {
        try {
            // Băm mật khẩu đầu vào bằng cùng thuật toán và chuyển đổi thành chuỗi hex string
            val hashedInputPassword = hashPassword(password!!)

            // So sánh mật khẩu đã băm trong cơ sở dữ liệu với mật khẩu đã băm từ mật khẩu đầu vào
            return hashedInputPassword == hashedPassword
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }


}