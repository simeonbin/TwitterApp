import twitter4j.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TweetRetrieve {
    private Twitter twitterHandler;
    private List<PrintStream> outputStreams = new ArrayList<>();

    public TweetRetrieve(Twitter twitterHandler, List<PrintStream> outputStreams) {
        this.twitterHandler = twitterHandler;
        this.outputStreams = outputStreams;
    }

    public Status retrieveLiveTweets(String searchQuery) throws TwitterException, InterruptedException {
        List<Long> tweetIds = new ArrayList<>();
        Query query = new Query();
        query.setQuery(searchQuery);

        tweetRetrieveHomeTimeline();

        while (true) {

            List<Status> tweets = twitterHandler.search(query).getTweets();
            List<Status> newTweets = tweets.stream()
                    .filter(tweet -> !tweetIds.contains(tweet.getId()))
                    .collect(Collectors.toList());

            newTweets.forEach(x -> tweetIds.add(x.getId())); //Memorize displayed tweets

            //For each new Tweet
            for (Status tweet : newTweets) {
                //Print to every output stream
                for (PrintStream outputStream : this.outputStreams) {
                    outputStream.println(tweet.getUser().getScreenName()
                            + ": "
                            + tweet.getText());
                }
                Thread.sleep(5000); //Sleep 5 seconds between queries
            }
        }
    }

    public void tweetRetrieveHomeTimeline ( ) throws TwitterException, InterruptedException {

        // The factory instance is re-useable and thread safe.
        Twitter twitter = TwitterFactory.getSingleton();

        List<Status> statuses = twitter.getHomeTimeline();

        System.out.println("Showing home timeline.");
        for (Status status : statuses)  {
            System.out.println (status.getUser().getName() + ":" +   status.getText());
        }
    }


}

