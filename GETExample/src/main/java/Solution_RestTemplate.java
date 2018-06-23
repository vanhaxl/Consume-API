import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.util.UriComponentsBuilder;

public class Solution_RestTemplate {
    private final static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        System.out.println("Count: " + getCountries("un", 100090));

    }

    private static class Country {
        public String name;
        public long population;

        @Override
        public String toString() {
            return "Country [name=" + name + ", population=" + population + "]";
        }

    }

    public static class Response {
        public List<Country> data = new ArrayList<>();

        @Override
        public String toString() {
            return "Response [data=" + data + "]";
        }

    }

    public static int getCountries(String str, int p) {
        int i = 1;
        long count = 0;
        while (true) {
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromHttpUrl("https://jsonmock.hackerrank.com/api/countries").queryParam("page", i);
            Response result = restTemplate.getForObject(builder.toUriString(), Response.class);
            if (result.data.size() == 0) {
                break;
            }
            System.out.println(result);
            i++;
            count += result.data.stream()
                    .filter(c -> c.name.toLowerCase().contains(str.toLowerCase()) && c.population >= p).count();
        }
        return (int) count;
    }



}
