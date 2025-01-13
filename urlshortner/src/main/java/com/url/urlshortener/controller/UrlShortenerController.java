package com.url.urlshortener.controller;

import com.url.urlshortener.request.URLShortenerRequest;
import com.url.urlshortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class UrlShortenerController {

    @Autowired
    UrlShortenerService urlShortenerService;

    @PostMapping("create/short-url/")
    public String createShortUrl(@RequestBody URLShortenerRequest shortenerRequest){
        return urlShortenerService.createShortUrl(shortenerRequest);
    }

    @GetMapping("{url}")
    public ResponseEntity<Void> getUrl(@PathVariable String url) {
        String longUrl = urlShortenerService.getLongUrl(url);
        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)  // Use 301 status
                .header("Location", longUrl)           // Set redirect location
                .build();
    }



}

