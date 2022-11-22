package ch.zli.m223;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Set;
import java.util.HashSet;
import org.junit.jupiter.api.Test;

import ch.zli.m223.model.Category;
import ch.zli.m223.model.Entry;
import ch.zli.m223.model.Tag;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import java.time.LocalDateTime;

@QuarkusTest
public class EntryResourceTest {

    @Test
    public void testIndexEndpoint() {
        given()
          .when().get("/entries")
          .then()
             .statusCode(200)
             .body(is("[]"));
    }

    @Test
    public void testDeleteEndpoint() {
        /**
        Entry entry = new Entry();
        entry.setCheckIn(LocalDateTime.now());
        entry.setCheckOut(LocalDateTime.now().plusSeconds(10));

        Response toDelete = given()
          .contentType(ContentType.JSON)
          .body(entry)
          .when().post("/entries");
        */
        Response toDelete = this.createEntry();  

        given()
          .when().delete("/entries/" + toDelete.jsonPath().get("id"))
          .then()
          .statusCode(204);
    }


    @Test
    public void testCreateEndpoint() {
      Response toDelete = this.createEntry();

      given()
        .when().delete("/entries/" + toDelete.jsonPath().get("id"))
        .then()
        .statusCode(204);
    }

    @Test
    public void testUpdateEndpoint() {

        Response toUpdate = this.createEntry();

        Long id = Long.valueOf(toUpdate.jsonPath().get("id").toString());

        Entry updatedEntry = new Entry();
        updatedEntry.setCheckIn(LocalDateTime.now().plusDays(2));
        updatedEntry.setCheckOut(LocalDateTime.now().plusDays(2));
        updatedEntry.setId(id);

        given()
          .contentType(ContentType.JSON)
          .body(updatedEntry)
          .when().put("/entries/")
          .then()
          .statusCode(200);
    }

    private Response createEntry() {
      Category category1 = new Category();
      category1.setTitle("Category one");

      Set<Tag> tags = new HashSet<Tag> ();

      Tag tag1 = new Tag();
      tag1.setTitle("Tag 1");

      tags.add(tag1);

      Entry entry = new Entry();
      entry.setCheckIn(LocalDateTime.now());
      entry.setCheckOut(LocalDateTime.now().plusSeconds(10));
      entry.setTags(tags);

      return given()
        .contentType(ContentType.JSON)
        .body(entry)
        .when().post("/entries");
    }
}