package com.childrenofparadiseorphanage.api.controller

import com.childrenofparadiseorphanage.api.model.Donation
import com.childrenofparadiseorphanage.api.service.DonationService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class PaystackBody (var email: String, var amount: Long)
data class MyAccessCode(var accessCode: String, var donation: Donation)

@RestController
@RequestMapping("/donate")
class DonationController(
    private val service: DonationService
) {

    @PostMapping
    fun initializeTransaction(@RequestBody @Valid donation: Donation):MyAccessCode {

        val response = service.getAccessCode(donation)
        if (!response!!.status){
            throw IllegalAccessException()
        }

        donation.reference= response.data.reference
        val savedDonation=service.saveDonation(donation)
        return MyAccessCode(response.data.access_code,savedDonation)
    }
}