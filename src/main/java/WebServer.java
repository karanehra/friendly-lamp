import com.mongodb.client.*;
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
import java.util.concurrent.*;

public class WebServer {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello");

//        RunnableMethods tasks = new RunnableMethods();
//
//        ExecutorService serv = Executors.newSingleThreadExecutor();
//
//        serv.submit(tasks.task);
//        serv.submit(tasks.task);
//        serv.submit(tasks.task);
//
//        Future<String> fut = serv.submit(tasks.task2);
//        System.out.println(fut.get());

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("testdb");
        MongoCollection<Document> feeds = database.getCollection("feeds");
        MongoCollection<Document> articlesColl = database.getCollection("articlesnew");
        MongoCursor<Document> feedCursor = feeds.find().iterator();

        List<Document> articleDocuments = new ArrayList<>();
        int i = 20;
        while(i>0){
            System.out.println();
            URL feedUrl = new URL(feedCursor.next().getString("url"));

            SyndFeedInput feedInput = new SyndFeedInput();
            try {
                SyndFeed feed = feedInput.build(new XmlReader(feedUrl));

                List<SyndEntry> articles = feed.getEntries();

                for (SyndEntry article : articles) {
                    Article articleData = new Article(
                            article.getTitle(),
                            article.getDescription().getValue(),
                            article.getPublishedDate(),
                            article.getLink()
                    );
                    articleDocuments.add(articleData.getDocument());
                }
                i--;
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
        articlesColl.insertMany(articleDocuments);
        feedCursor.close();

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

    static class RunnableMethods {
        public Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Hello " + threadName);
        };
        public Callable<String> task2 = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                return "Hellooooooo";
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };
    }
}
