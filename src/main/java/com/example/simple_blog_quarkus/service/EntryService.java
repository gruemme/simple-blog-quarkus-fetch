package com.example.simple_blog_quarkus.service;

import com.example.simple_blog_quarkus.dto.EntryInput;
import com.example.simple_blog_quarkus.model.BlogUser;
import com.example.simple_blog_quarkus.model.Entry;
import com.example.simple_blog_quarkus.model.Image;
import com.example.simple_blog_quarkus.model.Tag;
import com.example.simple_blog_quarkus.repositories.EntryRepository;
import com.example.simple_blog_quarkus.repositories.TagRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class EntryService {

    private final EntryRepository entryRepository;
    private final TagRepository tagRepository;

    public EntryService(EntryRepository entryRepository, TagRepository tagRepository) {
        this.entryRepository = entryRepository;
        this.tagRepository = tagRepository;
    }

    public PanacheQuery<Entry> getAllEntries(Sort sort, Page page) {
        return entryRepository.findAll(sort).page(page);
    }

    public Long getAllEntriesCount() {
        return entryRepository.findAll().count();
    }

    public Optional<Entry> findById(Long entryId) {
        return entryRepository.findByIdOptional(entryId);
    }

    public List<Entry> getEntriesByAuthor(BlogUser author,Sort sort, Page page) {
        return entryRepository.findByAuthor(author, sort, page);
    }

    public List<Entry> getEntriesByTag(String tagName) {
        return entryRepository.findByTags_NameOrderByCreatedDesc(tagName);
    }

    public Entry createEntryForUser(EntryInput entryInput, BlogUser author) {
        Image titleImage = createImageIfExists(entryInput);
        Set<Tag> tags = getOrCreateTags(entryInput);

        Entry newEntryToSave = new Entry(entryInput.getTitle(), entryInput.getText(), titleImage, author, tags);
        tags.stream().forEach(t -> t.getEntries().add(newEntryToSave));

        entryRepository.persist(newEntryToSave);

        return entryRepository.findById(newEntryToSave.getId());
    }

    private Image createImageIfExists(EntryInput entryInput) {
        if (entryInput.getImageData() == null || entryInput.getImageData().length <= 0) {
            return null;
        }

        return new Image(entryInput.getImageContentType(), entryInput.getImageData());
    }

    private Set<Tag> getOrCreateTags(EntryInput entryInput) {
        Set<String> tagInput = entryInput.getTags() != null ?
                entryInput.getTags().stream()
                        .map(String::trim)
                        .filter(Predicate.not(String::isEmpty))
                        .collect(Collectors.toSet()) : Collections.emptySet();
        Set<Tag> tags = tagRepository.findByNameIn(tagInput);
        if (tags.size() >= tagInput.size()) {
            return tags;
        }

        Set<String> tagsInDb = tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
        Set<Tag> tagsNotInDb = entryInput.getTags().stream()
                .filter(t -> !tagsInDb.contains(t))
                .map(Tag::new)
                .collect(Collectors.toSet());
        return Stream.of(tags, tagsNotInDb)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
}
