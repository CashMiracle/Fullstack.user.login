package spring_boot_react_auth;

import static org.junit.jupiter.api.Assertions.*;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest(classes = SpringBootReactAuthApplication.class)
class MongoConnectionIntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testMongoTemplateIsNotNull() {
        assertNotNull(mongoTemplate, "MongoTemplate should be autowired");
    }

    @Test
    void testConnectionAndInsert() {
        // Given
        String collectionName = "test_connection";
        Document doc = new Document("name", "TestDocument")
                .append("timestamp", System.currentTimeMillis());

        // When
        Document inserted = mongoTemplate.insert(doc, collectionName);

        // Then
        assertNotNull(inserted.get("_id"), "Inserted document should have an _id");
        assertEquals("TestDocument", inserted.getString("name"));

        // Cleanup (optional)
        mongoTemplate.remove(new Document("_id", inserted.get("_id")), collectionName);
    }

    @Test
    void testDatabaseExists() {
        String dbName = mongoTemplate.getDb().getName();
        assertNotNull(dbName);
        // If your test properties set database to 'mydatabase', it will be that.
        // If you use embedded or fallback, it might be 'test'. This is a flexible check.
        assertTrue(dbName.equals("mydatabase") || dbName.equals("test"), 
            "Database name should be 'mydatabase' or 'test'");
    }
}
