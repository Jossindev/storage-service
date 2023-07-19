package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DeletedStoragesResponse;
import com.example.demo.dto.StorageRequest;
import com.example.demo.dto.StorageResponse;
import com.example.demo.service.StorageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/storages")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @GetMapping
    public ResponseEntity<List<StorageResponse>> getAllStorages() {
        List<StorageResponse> storages = storageService.getAllStorages();
        return ResponseEntity.ok(storages);
    }

    @PostMapping
    public ResponseEntity<StorageResponse> createStorage(@RequestBody @Valid StorageRequest request) {
        Integer storageId = storageService.createStorage(request);
        StorageResponse response = StorageResponse.builder()
            .id(storageId).build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<DeletedStoragesResponse> deleteStorages(@RequestParam("id") @Size(max = 200) String id) {
        List<Integer> ids = Stream.of(id.split(","))
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(Collectors.toList());
        DeletedStoragesResponse deletedStorages = storageService.deleteStorages(ids);
        return ResponseEntity.ok(deletedStorages);
    }

    @GetMapping("/test")
    public String testEndpointForUser(Principal principal) {
        String name = principal.getName();
        System.out.println("Accepted for principal with name:" + name);
        return name;
    }

    @GetMapping("/test2")
    public String testEndpointForUser2() {
        return "TEST";
    }
}
