package com.example.simple_blog_quarkus.controller;

import com.example.simple_blog_quarkus.dto.EntryInput;
import com.example.simple_blog_quarkus.model.BlogUser;
import com.example.simple_blog_quarkus.model.Entry;
import com.example.simple_blog_quarkus.repositories.BlogUserRepository;
import com.example.simple_blog_quarkus.service.EntryService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.quarkus.security.Authenticated;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import java.util.List;

import static com.example.simple_blog_quarkus.util.PaginationUtil.getSortFromQuery;

@Path("/entries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EntryController {

    private EntryService entryService;
    private BlogUserRepository blogUserRepository;

    public EntryController(EntryService entryService, BlogUserRepository blogUserRepository) {
        this.entryService = entryService;
        this.blogUserRepository = blogUserRepository;
    }

    @GET
    public Response getAllEntries(
            @QueryParam("sort") List<String> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("10") int pageSize) {
        // Idea from https://quarkus.io/guides/rest-data-panache#hr-generating-resources
        // But I did not find the getSortFromQuery, so I implemented it my self
        Page page = Page.of(pageIndex, pageSize);
        Sort sort = getSortFromQuery(sortQuery);
        PanacheQuery<Entry> query = entryService.getAllEntries(sort, page);
        //ist<Entry> entries = entryService.getAllEntries(pageIndex, pageSize);
        //TODO: warp in page
        return Response.ok(query.list()).build();
    }

    @GET
    @Path("/{entryId}")
    public Entry getEntryById(@PathParam("entryId") Long entryId) {
        return entryService.findById(entryId)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @POST
    @Authenticated
    @Transactional
    @SecurityRequirement(name = "BasicAuth")
    public Response createEntry(EntryInput entryInput, @Context SecurityContext securityContext) {
        String username = securityContext.getUserPrincipal().getName();
        BlogUser author = blogUserRepository.findByUsername(username)
                .orElseThrow(() -> new WebApplicationException(Response.Status.UNAUTHORIZED));

        Entry createdEntry = entryService.createEntryForUser(entryInput, author);
        return Response.status(Response.Status.CREATED).entity(createdEntry).build();
    }
}