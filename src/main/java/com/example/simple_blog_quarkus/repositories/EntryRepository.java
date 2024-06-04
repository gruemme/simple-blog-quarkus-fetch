package com.example.simple_blog_quarkus.repositories;


import com.example.simple_blog_quarkus.model.BlogUser;
import com.example.simple_blog_quarkus.model.Entry;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EntryRepository implements PanacheRepository<Entry> {

    public List<Entry> findByTags_NameOrderByCreatedDesc(String tagName) {
        return list("SELECT e FROM Entry e JOIN e.tags t WHERE t.name = ?1 ORDER BY e.created DESC", tagName);
    }

    public List<Entry> findByAuthor(BlogUser author, Sort sort, Page page) {
        return find("author", sort, author).page(page).list();
    }
}
