package models;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;

public class Article {
    private final String title;
    private final String description;
    private final Date publishedDate;
    private String articleURL;
    private String URLHash;

    public Article(String title, String description, Date publishedDate, String articleURL) throws Exception {
        this.title = title;
        this.description = description;
        this.publishedDate = publishedDate;
        this.articleURL = articleURL;
        this.URLHash = this.createURLHash();
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

    private String createURLHash() throws Exception {
        MessageDigest digest = MessageDigest.getInstance("md5");
        byte[] byteDigest = digest.digest(this.articleURL.getBytes());
        BigInteger num = new BigInteger(1, byteDigest);
        StringBuilder hexString = new StringBuilder(num.toString(16));

        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public Document getDocument() {
        return new Document("title", this.title)
                .append("description", this.description)
                .append("createdAt", this.publishedDate)
                .append("url", this.articleURL)
                .append("hash", this.URLHash);
    }

    public void printMembers() throws Exception {
        Field[] members = this.getClass().getDeclaredFields();
//        System.out.println(Arrays.toString(members));
        System.out.println(members[0].getName());
    }
}
