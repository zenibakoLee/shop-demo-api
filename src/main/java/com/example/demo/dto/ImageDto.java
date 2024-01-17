package com.example.demo.dto;

import com.example.demo.model.Image;

public record ImageDto(
        String url
) {
    public static ImageDto of(Image image) {
        return new ImageDto(image.url());
    }
}