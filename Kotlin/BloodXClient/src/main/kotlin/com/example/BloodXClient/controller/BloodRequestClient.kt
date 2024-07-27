package com.poc.bloodx.controller

import com.example.BloodXClient.model.DataModel.BloodRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.ui.Model
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/BloodX/BloodRequest")
@CrossOrigin("\${CrossOrigin}")
class BloodRequestClient {

    private val restTemplate = RestTemplate()

    @GetMapping("/")
    fun showIndex(): String {
        return "sample";
    }

    @PostMapping("/addBloodRequest")
    fun addBloodRequest(
        @RequestBody bloodRequest: BloodRequest
    ): ResponseEntity<BloodRequest> {
        println()
        println("sdfadsfjdfkkjdfkjs;aldddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd")
        // Assume createBloodRequest returns BloodRequest
        val anotherServerUrl = "http://localhost:1010//BloodX//BloodRequest//Create"

        println(anotherServerUrl)
        // Send the bloodRequest object to another server and get the response
        val responseEntity: ResponseEntity<BloodRequest> =
            restTemplate.postForEntity(anotherServerUrl, bloodRequest, BloodRequest::class.java)

        // Check the response status and handle accordingly
        return if (responseEntity.statusCode == HttpStatus.CREATED) {
            ResponseEntity(responseEntity.body, HttpStatus.CREATED)
        } else {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}
