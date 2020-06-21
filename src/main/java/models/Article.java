package models;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Date;

public class Article {
    private final String title;
    private final String description;
    private final Date publishedDate;

    public Article(String title, String description, Date publishedDate) {
        this.title = title;
        this.description = description;
        this.publishedDate = publishedDate;
    }

    public void save(MongoCollection<Document> coll) {
        Document doc = new Document("title", this.title)
                .append("description", this.description)
                .append("createdAt", this.publishedDate);
        coll.insertOne(doc);
    }
}
