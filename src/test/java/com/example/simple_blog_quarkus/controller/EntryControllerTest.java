package com.example.simple_blog_quarkus.controller;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@QuarkusTest
@Disabled // TODO: Fix auto start of test containers
class EntryControllerTest {

    private static final String SIMPLE_ENTRY_REQUEST_BODY = """
            {
                "title":"Hello",
                "text": "This is my blog"
            }
            """;

    private static final String ENTRY_REQUEST_BODY_WITH_TAGS = """
            {
                "title":"Hello",
                "text": "This is my blog",
                "tags": ["hello","world"]
            }
            """;

    private static final String ENTRY_REQUEST_BODY_WITH_IMAGE = """
            {
                "title":"My first picture in my blog",
                "text": "This is my blog",
                "imageContentType": "application/png",
                "imageData": "iVBORw0KGgoAAAANSUhEUgAAAMgAAADIAQMAAACXljzdAAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAABlBMVEUAAP7////DYP5JAAAAAWJLR0QB/wIt3gAAAAlwSFlzAAALEgAACxIB0t1+/AAAAAd0SU1FB+QIGBcKN7/nP/UAAAAcSURBVFjD7cGBAAAAAMOg+VNf4QBVAQAAAAB8BhRQAAEAnyMVAAAAGXRFWHRjb21tZW50AENyZWF0ZWQgd2l0aCBHSU1Q569AywAAACV0RVh0ZGF0ZTpjcmVhdGUAMjAyMC0wOC0yNFQyMzoxMDo1NSswMzowMJB3XrkAAAAldEVYdGRhdGU6bW9kaWZ5ADIwMjAtMDgtMjRUMjM6MTA6NTUrMDM6MDDhKuYFAAAAAElFTkSuQmCC"
            }
            """;

    @Test
    public void postSimpleEntryAndCorrectContent() {
        given()
            .when()
                .body(SIMPLE_ENTRY_REQUEST_BODY)
                .contentType(MediaType.APPLICATION_JSON).auth()
                .basic("alice", "alice")
                .post("/entries")
            .then()
                .statusCode(201)
                .body(containsString("This is my blog"));
    }

    @Test
    public void postEntryWithTagsAndExpectTwoCorrectTagsSuccess() {
        given()
            .when()
                .body(ENTRY_REQUEST_BODY_WITH_TAGS)
                .contentType(MediaType.APPLICATION_JSON).auth()
                .basic("bob", "bob")
                .post("/entries")
            .then()
                .statusCode(201)
                .body("tags", hasSize(2))
                .body("tags.name", containsInAnyOrder("hello", "world")); ;
    }

    @Test
    public void postEntryWithImageAndExpectImageAndTypeIsCorrect() {
        given()
            .when()
                .body(ENTRY_REQUEST_BODY_WITH_IMAGE)
                .contentType(MediaType.APPLICATION_JSON).auth()
                .basic("alice", "alice")
                .post("/entries")
            .then()
                .statusCode(201)
                .body("titleImage.imageContent", hasLength(468))
                .body("titleImage.contentType", is("application/png")); ;
    }
}