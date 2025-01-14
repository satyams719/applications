package com.url.urlshortener.service;

import com.url.urlshortener.config.Base62Encoder;
import com.url.urlshortener.config.ZookeeperClient;
import com.url.urlshortener.model.UrlMapping;
import com.url.urlshortener.repository.UrlMappingRepository;
import com.url.urlshortener.request.URLShortenerRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Optional;

@Service
public class UrlShortenerService {

    private static String DOMAIN = "http://localhost:8081/api/v1/";

    private final ZookeeperClient zookeeperClient;
    private final UrlMappingRepository urlMappingRepository;

    public UrlShortenerService(ZookeeperClient zookeeperClient, UrlMappingRepository urlMappingRepository) {
        this.zookeeperClient = zookeeperClient;
        this.urlMappingRepository = urlMappingRepository;
    }

    public String createShortUrl(URLShortenerRequest urlShortenerRequest){
        String longUrl = urlShortenerRequest.getLongUrl();
        Optional<UrlMapping> urlMappingOptional = urlMappingRepository.findByLongUrl(longUrl);
        if (urlMappingOptional.isPresent()){
            return DOMAIN+urlMappingOptional.get().getShortId();
        }

        String uniqueId = zookeeperClient.generateUniqueId();
        String shortId = Base62Encoder.generate7CharBase62(Long.parseLong(uniqueId));
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl(longUrl);
        urlMapping.setShortId(shortId);
        urlMappingRepository.save(urlMapping);
        return DOMAIN+shortId;
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