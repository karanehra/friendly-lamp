import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import models.Article;
import org.bson.Document;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebServer {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello");

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("testdb");
        MongoCollection<Document> collection = database.getCollection("articlesnew");
        System.out.println(collection.countDocuments());

        URL feedUrl = new URL("https://timesofindia.indiatimes.com/rssfeedstopstories.cms");

        SyndFeedInput feedInput = new SyndFeedInput();
        SyndFeed feed = feedInput.build(new XmlReader(feedUrl));

        List<SyndEntry> articles = feed.getEntries();

        List<Document> articleDocuments = new ArrayList<>();

        for (SyndEntry article : articles) {
            Article articleData = new Article(
                    article.getTitle(),
                    article.getDescription().getValue(),
                    article.getPublishedDate(),
                    article.getLink()
            );
            articleDocuments.add(articleData.getDocument());
//            System.out.println(article.getPublishedDate());
            articleData.printMembers();
        }
        collection.insertMany(articleDocuments);

    }

    void setupHttpServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println(" Server started on port 8001");
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            byte[] response = "Welcome Real's HowTo test page".getBytes();
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }
    }
}
