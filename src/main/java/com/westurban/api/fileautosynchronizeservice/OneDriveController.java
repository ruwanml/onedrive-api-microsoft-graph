package com.westurban.api.fileautosynchronizeservice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/onedrive")
public class OneDriveController {
    private final OneDriveService oneDriveService;

    public OneDriveController(OneDriveService oneDriveService) {
        this.oneDriveService = oneDriveService;
    }
    
    @GetMapping("/login")
    public String login(Model model) {
		return "login";
	}

    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            oneDriveService.uploadFile(fileBytes, fileName);
        } catch (Exception e) {
            // Handle exceptions (e.g., file upload failure)
        }
    }
}

