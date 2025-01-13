package com.url.urlshortener.repository;

import com.url.urlshortener.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findByShortId(String longUrl);

    Optional<UrlMapping> findByLongUrl(String longUrl);
}
