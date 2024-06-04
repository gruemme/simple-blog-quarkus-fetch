package com.example.simple_blog_quarkus.controller;

import com.example.simple_blog_quarkus.dto.TagCount;
import com.example.simple_blog_quarkus.model.Entry;
import com.example.simple_blog_quarkus.repositories.TagRepository;
import com.example.simple_blog_quarkus.service.EntryService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/tags")
public class TagController {

    private final EntryService entryService;
    private final TagRepository tagRepository;

    public TagController(EntryService entryService, TagRepository tagRepository) {
        this.entryService = entryService;
        this.tagRepository = tagRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TagCount> getAllTags() {
        return tagRepository.findTagCount();
    }

    @GET
    @Path("{tagname}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<Entry> getEntriesByTag(String tagname) {
        return entryService.getEntriesByTag(tagname);
    }
}
