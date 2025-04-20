package com.childrenofparadiseorphanage.api.service

import com.childrenofparadiseorphanage.api.controller.PaystackBody
import com.childrenofparadiseorphanage.api.model.Donation
import com.childrenofparadiseorphanage.api.repository.DonationRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.core.env.Environment
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import javax.swing.Spring

data class PaystackResponse(
    val status: Boolean,
    val message: String,
    val data: PaystackData
)

data class PaystackData(
    val authorization_url: String,
    val access_code: String,
    val reference: String
)

@Service
class DonationService(private val env: Environment,private val db: DonationRepository) {

    fun saveDonation(donation: Donation)=db.save(donation)

    fun getAccessCode(donation: Donation): PaystackResponse? {
        val restTemplate = RestTemplate()
        val url = "https://api.paystack.co/transaction/initialize"
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            setBearerAuth(env.getProperty("paystack.secret")!!)
        }
        val body = PaystackBody(email = donation.email, amount = donation.amount)

        val entity = HttpEntity(body,headers)

        val response: ResponseEntity<String?> = restTemplate.postForEntity(url,entity,String::class.java)
        val responseBody = response.body ?: throw IllegalStateException("No response body")
        val paystackResponse = jacksonObjectMapper().readValue(responseBody, PaystackResponse::class.java)
        return paystackResponse
    }
}