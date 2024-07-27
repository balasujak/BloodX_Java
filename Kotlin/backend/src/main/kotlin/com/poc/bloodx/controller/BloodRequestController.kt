package com.poc.bloodx.controller

import com.poc.bloodx.model.BloodRequest
import com.poc.bloodx.service.BloodRequestService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.http.MediaType

@RestController
@RequestMapping("/BloodX/BloodRequest")
@CrossOrigin("\${CrossOrigin}")
class BloodRequestController(private val bloodRequestService: BloodRequestService) {

    @PostMapping(value = ["/Create"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @PreAuthorize("hasRole('admin')")
    fun createAdmin(@RequestBody bloodRequest: BloodRequest) : ResponseEntity<BloodRequest>{
        val savedBloodRequest = bloodRequestService.saveAdmin(bloodRequest)
        return ResponseEntity(savedBloodRequest, HttpStatus.CREATED)
    }

    @GetMapping("/GetAll")
    fun getAllBloodRequest(): ResponseEntity<List<BloodRequest>> {
        val bloodRequest = bloodRequestService.getAllBloodRequest()
        return ResponseEntity(bloodRequest, HttpStatus.OK)
    }
}