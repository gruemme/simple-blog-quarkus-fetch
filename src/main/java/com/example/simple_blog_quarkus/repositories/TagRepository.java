package com.example.simple_blog_quarkus.repositories;

import com.example.simple_blog_quarkus.dto.TagCount;
import com.example.simple_blog_quarkus.model.Tag;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class TagRepository implements PanacheRepository<Tag> {

    public Set<Tag> findByNameIn(Collection<String> names) {
        return find("name IN (:names)", Parameters.with("names", names))
                .stream()
                .collect(Collectors.toSet());
    }

    public List<TagCount> findTagCount() {
        return find("SELECT t.name, COUNT(e) FROM Tag t LEFT JOIN t.entries e GROUP BY t ORDER BY COUNT(e) DESC")
                .project(TagCount.class)
                .list();
    }
}
