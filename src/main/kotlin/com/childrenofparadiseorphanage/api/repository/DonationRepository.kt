package com.childrenofparadiseorphanage.api.repository

import com.childrenofparadiseorphanage.api.model.Donation
import org.springframework.data.repository.CrudRepository

interface DonationRepository: CrudRepository<Donation, Long>