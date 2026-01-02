package com.childrenofparadiseorphanage.api.controller

import com.childrenofparadiseorphanage.api.model.Status
import com.childrenofparadiseorphanage.api.service.DonationService
import com.childrenofparadiseorphanage.api.service.VerifyPaystackWebhook
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@JsonIgnoreProperties(ignoreUnknown = true)
data class PaystackHookRequest(var data: StatusAndAmount)

@JsonIgnoreProperties(ignoreUnknown = true)
data class StatusAndAmount(
    val status: String,
    val amount: Long,
    val reference: String
)

@RestController
@RequestMapping("/webhook")
class WebhookController(private var verifyPaystackWebhook: VerifyPaystackWebhook, private var service: DonationService) {

    @PostMapping("/paystack/hook")
    fun paystackWebhook(@RequestHeader("x-paystack-signature") signature: String,@RequestBody body: String): ResponseEntity.BodyBuilder {

        val isValid=verifyPaystackWebhook.verify(body,signature)
        print("validddd----- $isValid")
        if (!isValid){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
        }
        print("---------------------------------------------------------------------------------------hook received")
        print(body)
        val reqBody= jacksonObjectMapper().readValue(body, PaystackHookRequest::class.java)

        service.updatesFromHook(reqBody.data.reference, Status.valueOf(reqBody.data.status.trim().uppercase()),reqBody.data.amount)

        return ResponseEntity.ok()
    }

    @GetMapping("/paystack/{id}")
    fun pollingForStatus(@PathVariable id: Long): ResponseEntity<Map<String, Status?>?> {
        print(service.getStatus(id).status)
        return ResponseEntity.ok().body(mapOf("status" to service.getStatus(id).status))
    }
}