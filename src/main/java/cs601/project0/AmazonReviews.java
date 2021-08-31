package cs601.project0;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AmazonReviews {

    private Users users;
    private Products products;

    public AmazonReviews() {
        users = new Users();
        products = new Products();
    }

    public static void main(String[] args) {

        String inputFileLocation = null;
        String outputFileLocation = null;

        for (int i = 0; i < args.length; i = i + 2) {
            if(args[i].equals("-input")) {
                inputFileLocation = args[i + 1];
            }
            else if(args[i].equals("-output")) {
                outputFileLocation = args[i + 1];
            }
        }

        AmazonReviews amazonReviews = new AmazonReviews();
        //Verify input

        boolean isReadSuccess = amazonReviews.read(inputFileLocation);

        if(isReadSuccess) {
            amazonReviews.write(outputFileLocation);
        }

        amazonReviews.Dispose();

        System.out.println("Completed");
    }

    public boolean read(String fileLocation) {

        System.out.println("Reading file...");

        boolean flag = true;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileLocation), StandardCharsets.ISO_8859_1)) {
            String productId = null;
            String userId = null;
            float score = 0.0f;
            String line = br.readLine();

            while (line != null) {
                if(line.startsWith(Constants.productIdText)) {
                    productId = line.replace(Constants.productIdText, "");
                    products.upsertReview(productId);
                }
                else if(line.startsWith(Constants.userIdText)) {
                    userId = line.replace(Constants.userIdText, "");
                    users.upsert(userId);
                }
                else if(line.startsWith(Constants.scoreText)) {
                    score = Float.parseFloat(line.replace(Constants.scoreText, ""));
                    products.upsertScore(productId, score);
                }

                line = br.readLine();
            }
        }
        catch (IOException io) {
            System.out.printf("An error occurred while accessing file at a location %s. %s%n", fileLocation, io.getMessage());
            flag = false;
        }

        return flag;
    }

    public void write(String fileLocation) {

        System.out.println("Writing into a file...");

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileLocation), StandardCharsets.ISO_8859_1)) {

            bw.write(Constants.userLargestReviews);
            bw.newLine();
            ArrayList<String> ids = users.getUserIds(users.getMaxReview());
            for (String userId : ids) {
                bw.write(String.format("\t%s", userId));
                bw.newLine();
            }

            bw.write(Constants.productLargestReviews);
            bw.newLine();
            ids = products.getIdsWithMaxRev(products.getMaxReview());
            for (String productId : ids) {
                bw.write(String.format("\t%s", productId));
                bw.newLine();
            }

            bw.write(Constants.productMaxAvgScore);
            bw.newLine();
            ids = products.getIdsWithMaxScore(products.getMaxAvgScore());
            for (String productId : ids) {
                bw.write(String.format("\t%s", productId));
                bw.newLine();
            }
        }
        catch (IOException io) {
            System.out.printf("An error occurred while writing into the file at a location %s. %s%n", fileLocation, io.getMessage());
        }
    }

    public void Dispose() {
        users.Dispose();
        products.Dispose();
        users = null;
        products = null;
    }
}
