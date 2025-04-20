package com.childrenofparadiseorphanage.api.config

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import java.util.TimeZone

@Component
class TimeZoneConfig {

    @PostConstruct
    fun setupTimeZone(){
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        println("JVM TimeZone set to UTC")
    }
}