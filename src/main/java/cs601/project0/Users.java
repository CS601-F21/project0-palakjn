package cs601.project0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Users {

    private HashMap<String, Integer> reviewsMap;

    public Users() {
        reviewsMap = new HashMap<>();
    }

    public void upsert(String userId) {
        Integer value = reviewsMap.getOrDefault(userId, 0);
        reviewsMap.put(userId, value + 1);
    }

    public int getMaxReview() {
        int max = 0;

        for(Map.Entry entry : reviewsMap.entrySet()) {
            if((Integer)entry.getValue() > max) {
                max = (Integer)entry.getValue();
            }
        }

        return max;
    }

    public ArrayList<String> getUserIds(int reviewsCount) {
        ArrayList<String> userIds = new ArrayList<>();

        for(Map.Entry entry : reviewsMap.entrySet()) {
            if((Integer)entry.getValue() == reviewsCount) {
                userIds.add((String) entry.getKey());
            }
        }

        Collections.sort(userIds);
        return  userIds;
    }

    public void Dispose()
    {
        reviewsMap = null;
    }
}
