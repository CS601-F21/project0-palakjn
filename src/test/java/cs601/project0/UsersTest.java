package cs601.project0;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class UsersTest {

        static Users emptyUsers;
        static Users nonEmptyUsers;

        @BeforeAll
        public static void init() {
            emptyUsers = new Users();
            nonEmptyUsers = new Users();
            nonEmptyUsers.upsert("user1");
            nonEmptyUsers.upsert("user2");
            nonEmptyUsers.upsert("user1");
            nonEmptyUsers.upsert("user1");
            nonEmptyUsers.upsert("user3");
            nonEmptyUsers.upsert("user3");
        }

        @Test
        public void getMaxReviews_emptyMap_returnsZero() {
            Assertions.assertEquals(0, emptyUsers.getMaxReview());
        }

        @Test
        public void getMaxReviews_nonEmptyMap_returnsMax() {
            Assertions.assertEquals(3, nonEmptyUsers.getMaxReview());
        }

        @Test
        public void getUserIds_wrongInput_returnEmptyList() {
            Assertions.assertEquals(new ArrayList<String>(), nonEmptyUsers.getUserIds(4));
        }

        @Test
        public void getUserIds_correctInput_returnNonEmptyList() {
            ArrayList<String> expectedList = new ArrayList<>();
            expectedList.add("user1");
            Assertions.assertEquals(expectedList, nonEmptyUsers.getUserIds(3));
        }

        @AfterAll
        public static void dispose() {
            emptyUsers.Dispose();
            emptyUsers = null;
            nonEmptyUsers.Dispose();
            nonEmptyUsers = null;
        }
}
