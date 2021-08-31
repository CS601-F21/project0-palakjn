package cs601.project0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Products {

    private HashMap<String, Integer> reviews;
    private HashMap<String, Float> totalScores;

    public Products() {
        reviews = new HashMap<String, Integer>();
        totalScores = new HashMap<String, Float>();
    }

    public void upsertReview(String productId) {
        Integer value = reviews.getOrDefault(productId, 0);
        reviews.put(productId, value + 1);
    }

    public int getMaxReview() {
        int max = 0;

        for(Map.Entry entry : reviews.entrySet()) {
            if((Integer) entry.getValue() > max) {
                max = (Integer) entry.getValue();
            }
        }

        return max;
    }

    public ArrayList<String> getIdsWithMaxRev(int revCount) {
        ArrayList<String> productIds = new ArrayList<>();

        for (Map.Entry entry : reviews.entrySet()) {
            if((Integer) entry.getValue() == revCount) {
                productIds.add((String) entry.getKey());
            }
        }

        Collections.sort(productIds);
        return productIds;
    }

    public void upsertScore(String  productId, float score) {
        Float value = totalScores.getOrDefault(productId, 0.0f);
        totalScores.put(productId, value + score);
    }

    public float getMaxAvgScore() {
        float max = 0.0f;

        for(Map.Entry entry : totalScores.entrySet()) {
            Integer revCount = reviews.getOrDefault((String) entry.getKey(), null);

            if(revCount != null) {
                if(((Float) entry.getValue() / revCount) > max) {
                    max = (Float) entry.getValue() / revCount;
                }
            }
        }

        return max;
    }

    public ArrayList<String> getIdsWithMaxScore(float avgScore) {
        ArrayList<String> productIds = new ArrayList<>();

        for(Map.Entry entry : totalScores.entrySet()) {
            Integer revCount = reviews.getOrDefault((String) entry.getKey(), null);

            if(revCount != null) {
                if(((Float) entry.getValue() / revCount) == avgScore) {
                    productIds.add((String) entry.getKey());
                }
            }
        }

        Collections.sort(productIds);
        return productIds;
    }

    public void Dispose() {
        reviews = null;
        totalScores = null;
    }
}
