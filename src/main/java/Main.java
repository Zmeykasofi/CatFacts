import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.List;

public class Main {

    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {

        try {
            List<Fact> facts = mapper.readValue(
                    retrieveFacts(), new TypeReference<>() {
                    });
            facts.stream()
                    .filter(fact -> fact.getUpvotes() != null)
                    .filter(fact -> !"0".equals(fact.getUpvotes()))
                    .forEach(System.out::println);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String retrieveFacts() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(
                    "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats"
            );

            CloseableHttpResponse response = httpClient.execute(request);

            return (new String(response.getEntity().getContent().readAllBytes()));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
