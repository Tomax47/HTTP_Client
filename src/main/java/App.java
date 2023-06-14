import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class App {

    /**

     #We used the jsonPlaceHolder API for providing the fake json data, to manipulate and test our code on!

     */
    private static final String POSTS_API_URL = "https://jsonplaceholder.typicode.com/posts";
    public static void main(String[] args) {

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().GET()
                    .header("accept","application/json")
                    .uri(URI.create(POSTS_API_URL))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
            System.out.println("\nList of posts : ");


            /** Now let's parse the json into objects -> */

            ObjectMapper mapper = new ObjectMapper();
            /**

             #The TypeReference<List<Post>>() {} is there to tell the mapper that we want u to read the json data, and return them into objects of the type Post!

             #In the posts.forEach(() method, we can print the posts by passing through each post in the posts list, and call the Stream.out() method on it, so then it calls the println and print it out!
              -> form would have been :
                    posts.forEach(System.out::println);
             */
            List<Post> posts = mapper.readValue(response.body(), new TypeReference<List<Post>>() {});

            posts.forEach(n -> {
                System.out.println(n+"\n");
            });

        } catch (InterruptedException e) {
            System.out.println(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
