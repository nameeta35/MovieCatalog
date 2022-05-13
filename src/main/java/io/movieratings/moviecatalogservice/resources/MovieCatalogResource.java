package io.movieratings.moviecatalogservice.resources;
import io.movieratings.moviecatalogservice.models.CatalogItem;
import io.movieratings.moviecatalogservice.models.Rating;
import io.movieratings.moviecatalogservice.models.Movie;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.security.IdentityScope;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @RequestMapping("/{userID}")
    public List<CatalogItem> getCatalog(@PathVariable("userID") String userID){

        RestTemplate restTemplate = new RestTemplate();

        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 4),
                new Rating("5678", 3)
                );

        return ratings.stream().map(rating->{
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/foo" + rating.getMovieID(), Movie.class);
            return new CatalogItem(movie.getName(), "Test", rating.getRating());
        })
                .collect(Collectors.toList());

    }
}
