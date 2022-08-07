package com.codingoutreach.clubservice.service;

import com.codingoutreach.clubservice.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ImageService {
    private ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    public void uploadProfilePicture(String path, UUID clubId) {
        imageRepository.uploadProfilePicture(path, clubId);
    }

    public byte[] getProfilePicture(UUID clubId) {
        return imageRepository.getProfilePicture(clubId);
    }
}
