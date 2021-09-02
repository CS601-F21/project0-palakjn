package cs601.project0;

import java.io.*;
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

        //Reading arguments
        for (int i = 0; i < args.length; i = i + 2) {
            if(args[i].equals("-input") && i + 1 < args.length) {
                //Next index if exists in an array will be the value
                inputFileLocation = args[i + 1];
            }
            else if(args[i].equals("-output") && i + 1 < args.length) {
                //Next index if exists in an array will be the value
                outputFileLocation = args[i + 1];
            }
        }

        AmazonReviews amazonReviews = new AmazonReviews();

        //Validation of arguments whether a file exists at a value specified after -input flag.
        boolean isValid = amazonReviews.validate(inputFileLocation, outputFileLocation);

        if(isValid) {
            //Reading file.
            boolean isReadSuccess = amazonReviews.read(inputFileLocation);

            if (isReadSuccess) {
                //Writing the output to a file in a proper format.
                amazonReviews.write(outputFileLocation);
            }
        }

        amazonReviews.Dispose();
    }

    /** Validates whether given inputs are not null and files exist at a given file locations.
     * @param inputFileLocation The path to a file
     * @param outputFileLocation The path to a file
     * @return returns true if inputs are not null and files exist at a given location else false
     */
    public boolean validate(String inputFileLocation, String outputFileLocation) {
        boolean flag = false;

        if(Strings.isNullOrEmpty(inputFileLocation) || Strings.isNullOrEmpty(outputFileLocation)) {
            System.out.println("Passed arguments are incorrect");
        }
        else if(!Files.exists(Paths.get(inputFileLocation))) {
            System.out.printf("No file found at a location %s", inputFileLocation);
        }
        else {
            flag = true;
        }

        return flag;
    }

    /** Read the file line by line, fetch productId, userId, score from a file, and send the information to the Products and Users class.
     * @param fileLocation The path to a file
     * @return returns true if reading is successful else false
     */
    public boolean read(String fileLocation) {
        boolean flag = false;

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileLocation), StandardCharsets.ISO_8859_1)) {
            String productId;
            String userId;
            float score;
            String line = br.readLine();

            while (line != null) {
                //A block of lines having details of one product will start from the line 'product/productId'.
               if(line.startsWith(Constants.productIdText)) {
                   productId = line.replace(Constants.productIdText, "");

                   line = br.readLine();

                   //Immediate next line will be 'review/userId'.
                   if(line.startsWith(Constants.userIdText) && !Strings.isNullOrEmpty(productId)) {
                       userId = line.replace(Constants.userIdText, "");

                       //The third line from 'review/userId' will be 'review/score'.
                       for (int i = 1; i <= 3; i++) {
                           line = br.readLine();
                       }

                       if(line.startsWith(Constants.scoreText) && !Strings.isNullOrEmpty(userId)) {
                           score = Float.parseFloat(line.replace(Constants.scoreText, ""));

                           //Inserting values to HashMap once get the full details of one product.
                           products.upsertReview(productId);
                           products.upsertScore(productId, score);
                           users.upsert(userId);

                           flag = true;
                       }
                   }
               }

                line = br.readLine();
            }
        }
        catch (IOException io) {
            StringWriter writer = new StringWriter();
            io.printStackTrace(new PrintWriter(writer));

            System.out.printf("An error occurred while accessing file at a location %s. %s", fileLocation, writer);
            flag = false;
        }

        return flag;
    }

    /** Opens or creates a file for writing userIds with the largest number of reviews, products with the largest number of reviews, and products with the highest average score.
     * @param fileLocation The path to a file
     */
    public void write(String fileLocation) {
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

    /** Nullify the map object from memory
     */
    public void Dispose() {
        users.Dispose();
        products.Dispose();
        users = null;
        products = null;
    }
}
