import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

public class TwitterApp {
    public static void main(String[] args) throws TwitterException, InterruptedException, IOException {
        // RequestToken requestToken = null;
        Twitter twitter = new TwitterFactory().getInstance();

       /* RequestToken requestToken = twitter.getOAuthRequestToken();
        System.out.println("Please visit this URL: " + requestToken.getAuthenticationURL());

        Scanner scanner = new Scanner(System.in);
        String pin = scanner.nextLine();
        AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, pin);
        System.out.println(accessToken.getScreenName() );
        System.out.println(accessToken.getToken());
        System.out.println(accessToken.getTokenSecret());*/

        TweetPublish tweetPublisher = new TweetPublish(twitter);
        tweetPublisher.publish("Test tweet 100");
        Thread.sleep(1000);
        tweetPublisher.publish("Test tweet 101");
        Thread.sleep(1000);
        tweetPublisher.publish("Test tweet 102");
        Thread.sleep(1000);

        //new TweetRetrieve that should print to the console and a file
        PrintStream fileOutput = new PrintStream( new FileOutputStream("outputFile.txt") );
        PrintStream consoleOutput = System.out;

        TweetRetrieve tweetRetriever = new TweetRetrieve(twitter, Arrays.asList(consoleOutput, fileOutput));
        tweetRetriever.retrieveLiveTweets("#HSBC");
        fileOutput.close();

    }

}
