package com.westurban.api.fileautosynchronizeservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OneDriveService {
    @Value("${onedrive.api.url}")
    private String apiUrl;

    @Value("${onedrive.api.access-token}")
    private String accessToken;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OneDriveService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public void uploadFile(byte[] fileBytes, String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(fileBytes, headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(
                apiUrl + "/drive/root:/" + fileName + ":/content",
                HttpMethod.PUT,
                requestEntity,
                JsonNode.class
        );

        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new RuntimeException("Failed to upload file to OneDrive");
        }
    }

    // Implement other methods for syncing, listing, deleting, etc.
}

