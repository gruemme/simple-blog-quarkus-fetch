package com.example.simple_blog_quarkus.controller;

import com.example.simple_blog_quarkus.model.BlogUser;
import com.example.simple_blog_quarkus.model.Entry;
import com.example.simple_blog_quarkus.repositories.BlogUserRepository;
import com.example.simple_blog_quarkus.service.EntryService;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.quarkus.security.Authenticated;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.Optional;

import static com.example.simple_blog_quarkus.util.PaginationUtil.getSortFromQuery;

@Path("/users")
public class BlogUserController {

    private final BlogUserRepository blogUserRepository;
    private final EntryService entryService;

    public BlogUserController(BlogUserRepository blogUserRepository, EntryService entryService) {
        this.blogUserRepository = blogUserRepository;
        this.entryService = entryService;
    }

    @GET
    @Path("me")
    @Authenticated
    @SecurityRequirement(name = "BasicAuth")
    public BlogUser getCurrentUser(@Context SecurityContext securityContext) {
        String username = securityContext.getUserPrincipal().getName();
        Optional<BlogUser> author = blogUserRepository.findByUsername(username);

        return author.orElseThrow( () -> new WebApplicationException(Response.Status.UNAUTHORIZED));
    }

    @GET
    @Path("{username}/entries")
    public List<Entry> getEntriesByUser(String username,
                                        @QueryParam("sort") List<String> sortQuery,
                                        @QueryParam("page") @DefaultValue("0") int pageIndex,
                                        @QueryParam("size") @DefaultValue("10") int pageSize) {
        Optional<BlogUser> blogUserOptional = blogUserRepository.findByUsername(username);
        BlogUser author = blogUserOptional.orElseThrow(() -> new WebApplicationException(HttpResponseStatus.NOT_FOUND.code()));
        Page page = Page.of(pageIndex, pageSize);
        Sort sort = getSortFromQuery(sortQuery);
        return entryService.getEntriesByAuthor(author, sort, page);
    }
}
