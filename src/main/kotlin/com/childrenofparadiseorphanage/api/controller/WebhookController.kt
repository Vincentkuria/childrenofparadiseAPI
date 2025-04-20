package com.childrenofparadiseorphanage.api.controller

import com.childrenofparadiseorphanage.api.service.DonationService
import com.childrenofparadiseorphanage.api.service.VerifyPaystackWebhook
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/webhook")
class WebhookController(private var verifyPaystackWebhook: VerifyPaystackWebhook, private var service: DonationService) {

    @PostMapping("/paystack")
    fun paystackWebhook(@RequestHeader("x-paystack-signature") signature: String,request: HttpServletRequest,@RequestBody body: String): ResponseEntity.BodyBuilder {

        var isValid=verifyPaystackWebhook.verify(request.inputStream.readBytes(),signature)

        if (!isValid){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
        }
//        TODO: use the data.status & data.amount to update db and give response

        print(body)

        return ResponseEntity.ok()
    }
}