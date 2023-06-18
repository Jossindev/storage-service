package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.DeletedStoragesResponse;
import com.example.demo.dto.StorageRequest;
import com.example.demo.dto.StorageResponse;
import com.example.demo.model.Storage;
import com.example.demo.repository.StorageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final StorageRepository repository;

    public Integer createStorage(final StorageRequest request) {
        Storage storage = convertToEntity(request);
        return repository.save(storage).getId();
    }

    public List<StorageResponse> getAllStorages() {
        return repository.findAll().stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public DeletedStoragesResponse deleteStorages(final List<Integer> ids) {
        repository.deleteAllById(ids);
        return DeletedStoragesResponse.builder()
            .ids(ids)
            .build();
    }


    private StorageResponse convertToResponse(Storage storage) {
        return StorageResponse.builder()
            .id(storage.getId())
            .storageType(storage.getStorageType())
            .bucket(storage.getBucket())
            .path(storage.getPath())
            .build();
    }

    public Storage convertToEntity(final StorageRequest request) {
        return Storage.builder()
            .storageType(request.getStorageType())
            .path(request.getPath())
            .bucket(request.getBucket())
            .build();
    }
}
