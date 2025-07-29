package com.childrenofparadiseorphanage.api.service

import org.springframework.core.env.Environment
import org.springframework.security.crypto.codec.Hex
import org.springframework.stereotype.Service
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class VerifyPaystackWebhook(private val env: Environment) {

    private val paystackSecretKey= env.getProperty("paystack.secret")?:throw IllegalStateException("paystack.secret property is required")

    fun verify(payload: ByteArray, signature: String): Boolean{
        //create a key (secretKeySpec) with paystackSecretKey's bytearray & HmacSHA512 algorithm
        val secretKeySpec= SecretKeySpec(paystackSecretKey.toByteArray(),"HmacSHA512")
        val mac= Mac.getInstance("HmacSHA512")
        mac.init(secretKeySpec)
        val hash =mac.doFinal(payload)
        val generatedSignature= String(Hex.encode(hash))
        return generatedSignature.equals(signature,ignoreCase = true)
    }
}