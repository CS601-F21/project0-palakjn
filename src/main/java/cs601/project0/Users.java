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

    /** Insert UserId as a key and set the value as one or increase the number of reviews by one if userId already exists.
     * @param userId UniqueId of a user
     */
    public void upsert(String userId) {
        Integer value = reviewsMap.getOrDefault(userId, 0);
        reviewsMap.put(userId, value + 1);
    }

    /** Search through the map and find the maximum number of reviews one or more user has.
     * @return The maximum value of the number of reviews
     */
    public int getMaxReview() {
        int max = 0;

        for(Map.Entry<String, Integer> entry : reviewsMap.entrySet()) {
            if(entry.getValue() > max) {
                max = entry.getValue();
            }
        }

        return max;
    }

    /** Search through the map and finds all the userIds with the given number of reviews.
     * @param reviewsCount The number of reviews
     * @return The list containing all the user's IDs with the given review count.
     */
    public ArrayList<String> getUserIds(int reviewsCount) {
        ArrayList<String> userIds = new ArrayList<>();

        for(Map.Entry<String, Integer> entry : reviewsMap.entrySet()) {
            if(entry.getValue() == reviewsCount) {
                userIds.add(entry.getKey());
            }
        }

        Collections.sort(userIds);
        return  userIds;
    }

    /** Nullify the map object from memory
     */
    public void Dispose()
    {
        reviewsMap = null;
    }
}
