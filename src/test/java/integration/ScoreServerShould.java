package integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.king.juan.simon.scores.server.ScoreServer;

public class ScoreServerShould {
    private static final ScoreServer scoreServer = new ScoreServer();

    @BeforeClass
    public static void init() throws IOException {
            scoreServer.start(8080);
    }

    @AfterClass
    public static void shutdown() {
        scoreServer.stop(0);
    }

    @Test
    public void provideSessionKey() throws IOException {
        HttpClient client =  HttpClientBuilder.create().disableRedirectHandling().build();
        String uri = "http://127.0.0.1:8080/123/login";
        HttpUriRequest request = new HttpPost(uri);
        HttpResponse response = client.execute(request);
        assertEquals(200,response.getStatusLine().getStatusCode());
        assertNotNull(response.getEntity().getContent().toString());
    }

    @Test
    public void saveScores() throws IOException {
        HttpClient client =  HttpClientBuilder.create().disableRedirectHandling().build();
        String uri = "http://127.0.0.1:8080/123/login";
        HttpUriRequest request = new HttpPost(uri);
        HttpResponse response = client.execute(request);
        assertEquals(200,response.getStatusLine().getStatusCode());

        String sessionKey = EntityUtils.toString(response.getEntity());
        uri = "http://127.0.0.1:8080/1/score?sessionkey=" + sessionKey;
        request = new HttpPost(uri);
        ((HttpPost) request).setEntity(new ByteArrayEntity("1000".getBytes("UTF-8")));
        response = client.execute(request);
        assertEquals(200,response.getStatusLine().getStatusCode());
    }

    @Test
    public void retrieveHighScores() throws IOException {
        HttpClient client =  HttpClientBuilder.create().disableRedirectHandling().build();
        String uri = "http://127.0.0.1:8080/123/login";
        HttpUriRequest request = new HttpPost(uri);
        HttpResponse response = client.execute(request);
        assertEquals(200,response.getStatusLine().getStatusCode());

        String sessionKey = EntityUtils.toString(response.getEntity());
        uri = "http://127.0.0.1:8080/2/score?sessionkey=" + sessionKey;
        request = new HttpPost(uri);
        ((HttpPost) request).setEntity(new ByteArrayEntity("2000".getBytes("UTF-8")));
        client.execute(request);


        uri = "http://127.0.0.1:8080/1234/login";
        request = new HttpPost(uri);
        response = client.execute(request);
        sessionKey = EntityUtils.toString(response.getEntity());

        uri = "http://127.0.0.1:8080/2/score?sessionkey=" + sessionKey;
        request = new HttpPost(uri);
        ((HttpPost) request).setEntity(new ByteArrayEntity("4000".getBytes("UTF-8")));
        response = client.execute(request);

        client = HttpClientBuilder.create().disableRedirectHandling().build();
        uri = "http://127.0.0.1:8080/2/highscorelist";
        request = new HttpGet(uri);
        response = client.execute(request);
        String highScores = EntityUtils.toString(response.getEntity());
        assertEquals("1234=4000,123=2000",highScores);
    }
}
