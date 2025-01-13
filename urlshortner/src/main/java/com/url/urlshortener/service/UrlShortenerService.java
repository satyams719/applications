package com.url.urlshortener.service;

import com.url.urlshortener.model.UrlMapping;
import com.url.urlshortener.repository.UrlMappingRepository;
import com.url.urlshortener.request.URLShortenerRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Optional;

@Service
public class UrlShortenerService {

    private static String DOMAIN = "http://localhost:8080/api/v1/";

    private final UrlMappingRepository urlMappingRepository;

    public UrlShortenerService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public String createShortUrl(URLShortenerRequest urlShortenerRequest){
        String longUrl = urlShortenerRequest.getLongUrl();
        Optional<UrlMapping> urlMappingOptional = urlMappingRepository.findByLongUrl(longUrl);
        if (urlMappingOptional.isPresent()){
            return DOMAIN+urlMappingOptional.get().getShortId();
        }

        String uniqueId = generateUniqueId(longUrl);
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl(longUrl);
        urlMapping.setShortId(uniqueId);
        urlMappingRepository.save(urlMapping);
        return DOMAIN+uniqueId;
    }

    private String generateUniqueId(String longUrl) {
        return DigestUtils.md5DigestAsHex(longUrl.getBytes()).substring(0,6);
    }

    public String getLongUrl(String shortId) {
        return urlMappingRepository.findByShortId(shortId)
                .map(UrlMapping::getLongUrl)
                .orElseThrow(() -> new IllegalArgumentException("Short URL not found"));
    }
}