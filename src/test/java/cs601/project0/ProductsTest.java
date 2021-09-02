package cs601.project0;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ProductsTest {
    static Products emptyProducts;
    static Products nonEmptyProducts;

    @BeforeAll
    public static void init() {
        emptyProducts = new Products();
        nonEmptyProducts = new Products();

        //Mapping each productId to the number of reviews.
        nonEmptyProducts.upsertReview("product1");
        nonEmptyProducts.upsertReview("product2");
        nonEmptyProducts.upsertReview("product1");
        nonEmptyProducts.upsertReview("product1");
        nonEmptyProducts.upsertReview("product3");
        nonEmptyProducts.upsertReview("product3");

        //Mapping each productId to the total score.
        nonEmptyProducts.upsertScore("product1", 3.0f);
        nonEmptyProducts.upsertScore("product2", 4.6f);
        nonEmptyProducts.upsertScore("product1", 3.3f);
        nonEmptyProducts.upsertScore("product1", 4.0f);
        nonEmptyProducts.upsertScore("product3", 1.5f);
        nonEmptyProducts.upsertScore("product3", 3.2f);
    }

    @Test
    public void getMaxReviews_emptyMap_returnsZero() {
        Assertions.assertEquals(0, emptyProducts.getMaxReview());
    }

    @Test
    public void getMaxReviews_nonEmptyMap_returnsMax() {
        Assertions.assertEquals(3, nonEmptyProducts.getMaxReview());
    }

    @Test
    public void getIdsWithMaxRev_wrongInput_returnEmptyList() {
        Assertions.assertEquals(new ArrayList<String>(), nonEmptyProducts.getIdsWithMaxRev(6));
    }

    @Test
    public void getIdsWithMaxRev_correctInput_returnNonEmptyList() {
        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add("product3");
        Assertions.assertEquals(expectedList, nonEmptyProducts.getIdsWithMaxRev(2));
    }

    @Test
    public void getMaxScore_emptyMap_returnsZero() {
        Assertions.assertEquals(0.0f, emptyProducts.getMaxAvgScore());
    }

    @Test
    public void getMaxScore_nonEmptyMap_returnsMax() {
        Assertions.assertEquals(4.6f, nonEmptyProducts.getMaxAvgScore());
    }

    @Test
    public void getIdsWithMaxScore_wrongInput_returnEmptyList() {
        Assertions.assertEquals(new ArrayList<String>(), nonEmptyProducts.getIdsWithMaxScore(3.4f));
    }

    @Test
    public void getIdsWithMaxScore_correctInput_returnNonEmptyList() {
        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add("product2");
        Assertions.assertEquals(expectedList, nonEmptyProducts.getIdsWithMaxScore(4.6f));
    }

    @AfterAll
    public static void dispose() {
        emptyProducts.Dispose();
        emptyProducts = null;
        nonEmptyProducts.Dispose();
        nonEmptyProducts = null;
    }
}
