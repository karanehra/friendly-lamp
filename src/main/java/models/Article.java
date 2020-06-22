package models;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.net.URL;
import java.security.MessageDigest;
import java.util.Date;

public class Article {
    private final String title;
    private final String description;
    private final Date publishedDate;
    private final String articleURL;

    public Article(String title, String description, Date publishedDate, String articleURL) {
        this.title = title;
        this.description = description;
        this.publishedDate = publishedDate;
        this.articleURL = articleURL;
    }

    public Article(String title, String description, Date publishedDate) {
        this.title = title;
        this.description = description;
        this.publishedDate = publishedDate;
    }

    public void save(MongoCollection<Document> coll) {
        Document doc = this.getDocument();
        coll.insertOne(doc);
    }

    private void createURLHash() throws Exception {
        MessageDigest digest = MessageDigest.getInstance("md5");
        byte[] byteDigest = digest.digest(this.articleURL);
    }

    public Document getDocument() {
        return new Document("title", this.title)
                .append("description", this.description)
                .append("createdAt", this.publishedDate);
    }
}
