package com.example.pidetucomida.utils
import org.mindrot.jbcrypt.BCrypt

class UtilsChyper {

    fun hashPassword(password: String): String {
        val salt = BCrypt.gensalt()
        return BCrypt.hashpw(password, salt)
    }

}