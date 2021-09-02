package cs601.project0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Products {

    private HashMap<String, Integer> reviews;
    private HashMap<String, Float> totalScores;

    public Products() {
        reviews = new HashMap<>();
        totalScores = new HashMap<>();
    }

    /** Insert productId as a key and set the value as one or increase the number of reviews by one if productId already exists.
     * @param productId UniqueId of a product
     */
    public void upsertReview(String productId) {
        Integer value = reviews.getOrDefault(productId, 0);
        reviews.put(productId, value + 1);
    }

    /** Search through the map and find the maximum number of reviews one or more product has.
     * @return The maximum value of the number of reviews
     */
    public int getMaxReview() {
        int max = 0;

        for(Map.Entry<String, Integer> entry : reviews.entrySet()) {
            if(entry.getValue() > max) {
                max = entry.getValue();
            }
        }

        return max;
    }

    /** Search through the map and finds all the productIds with the given number of reviews.
     * @param revCount The number of reviews
     * @return The list containing all the product's IDs with the given review count.
     */
    public ArrayList<String> getIdsWithMaxRev(int revCount) {
        ArrayList<String> productIds = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : reviews.entrySet()) {
            if(entry.getValue() == revCount) {
                productIds.add(entry.getKey());
            }
        }

        Collections.sort(productIds);
        return productIds;
    }

    /** Insert productId as a key and set the value as the product's score or add the score to the existing one if productId already exists.
     * @param productId UniqueId of a product
     * @param score Score of a product
     */
    public void upsertScore(String  productId, float score) {
        Float value = totalScores.getOrDefault(productId, 0.0f);
        totalScores.put(productId, value + score);
    }

    /** Search through the map and find the maximum average score one or more product has.
     * @return The maximum average score of a product
     */
    public float getMaxAvgScore() {
        float max = 0.0f;

        for(Map.Entry<String, Float> entry : totalScores.entrySet()) {
            Integer revCount = reviews.getOrDefault(entry.getKey(), null);

            if(revCount != null) {
                if((entry.getValue() / revCount) > max) {
                    max = entry.getValue() / revCount;
                }
            }
        }

        return max;
    }

    /** Search through the map and finds all the productIds with a given score.
     * @param score Score of a product
     * @return The list containing all the product's IDs with the given score.
     */
    public ArrayList<String> getIdsWithMaxScore(float score) {
        ArrayList<String> productIds = new ArrayList<>();

        for(Map.Entry<String, Float> entry : totalScores.entrySet()) {
            Integer revCount = reviews.getOrDefault(entry.getKey(), null);

            if(revCount != null) {
                if((entry.getValue() / revCount) == score) {
                    productIds.add(entry.getKey());
                }
            }
        }

        Collections.sort(productIds);
        return productIds;
    }

    /** Nullify the map objects from memory
     */
    public void Dispose() {
        reviews = null;
        totalScores = null;
    }
}
