package Data_Project;

import facebook4j.*;
import facebook4j.ResponseList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;
import se.walkercrou.places.Review;
import twitter4j.GeoLocation;
import twitter4j.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.walkercrou.places.Place;


/**
 * Created by bart on 10-3-2015.
 */
public class AnalistController {


    @FXML
    public TextArea outputTempArea;

    @FXML
    public TextArea outputTextArea;
    @FXML
    public TextField inputTextArea;


    @FXML
    private void logoutButtonAction() {
        fxmlController logout = new fxmlController();
        logout.setLogin("Log in", "sample.fxml");
    }

    @FXML
    private void dataButtonAction() {
        fxmlController logout = new fxmlController();
        DataController dc = new DataController();
        logout.setMainStage("Data","Data.fxml");
    }

    @FXML
    private void TwitzoekButtonAction() {

            String inp = inputTextArea.getText();
            // input > twitternaam > return timelijn/tweets naar output

            try {
                outputTextArea.setText(" ");
                Twitter latestTweetChecker = new TwitterFactory().getInstance();
                List<Status> statuses = latestTweetChecker.getUserTimeline(inp);
                outputTextArea.appendText("Showing " + " " + inp + " " + "timeline.\r\n \r\n");
                for (Status status : statuses) {
                    outputTextArea.appendText(status.getUser().getName() + ":" +
                            status.getText() + "\r\n");
                }
            }catch (TwitterException te) {
                te.printStackTrace();
                outputTextArea.appendText("Failed : " + te.getMessage());
                System.exit(0);
            }
    }


    @FXML
    private void HashtaggButtonAction() { //twitter api non oob
        String input = inputTextArea.getText(); // kijkt wat je hebt getypt
        try {
            outputTextArea.setText(" "); // cleanup
            twitter4j.Twitter twitter =  TwitterFactory.getSingleton();
            Query query = new Query(input);
            QueryResult result = twitter.search(query);
            for (Status status : result.getTweets()) {// print uit

                String Usrname = status.getUser().getScreenName();
                int FollowerCount = status.getUser().getFollowersCount();
                int RetweetCount = status.getRetweetCount();
                String Message = status.getText();
                GeoLocation Location = status.getGeoLocation();
                int FavoriteCount = status.getFavoriteCount();
                String end = "=-=-=-=-=-=";


                outputTextArea.appendText( "Naam : @" + Usrname + " : "+
                        "\n\r volger count : " + FollowerCount +
                        "\n\r bericht : " + Message +
                        "\n\r locatie :" + Location +
                        "\n\r fav count:" + FavoriteCount +
                        "\n\r retweet count :" + RetweetCount +
                        "\n\r "+ end +
                        "\n\r"      );
            }
        }catch (TwitterException te) { // error message
            te.printStackTrace();
            outputTextArea.appendText("Failed : " + te.getMessage());
            System.exit(0);
        }
    }

    @FXML
    private void PlacesButtonAction() {

        GooglePlaces client = new GooglePlaces("AIzaSyALbXTMU7FfHrYpokHmOYpvJBsXUioQYlg");
        ArrayList<Place> places = (ArrayList<Place>) client.getPlacesByQuery("Euromast", GooglePlaces.MAXIMUM_RESULTS);
        int review = 0;
        List<Review> l1 = null;
        outputTextArea.setText(" "); // cleanup

        for (int i = 0; i < places.size(); i++) {
            Place me = places.get(i);
            outputTextArea.appendText(String.valueOf(me.getRating())+ "\n\r");
            // moet if omheen om de -1.0 (geen rating) verwijderen.
        }

    }

    @FXML
    private void FacebookButtonAction() throws FacebookException {

        Facebook facebook = new FacebookFactory().getInstance();
        ResponseList<Post> feeds = facebook.getFeed("313850611958467",/* < dit
        verwijst naar een OPENBARE  facebook pagina van rotterdam centraal */
          new Reading().limit(50));
        outputTextArea.setText(" "); // cleanup

        for (int i = 0; i < feeds.size(); i++) {
            Post post = feeds.get(i);
            String message = post.getMessage();
            // Print console test
            outputTextArea.appendText(message + "\n\r");
        }


    }
    @FXML
    private void WeerButtonAction() throws IOException {
        new weerInfo();
        weerInfo info = new weerInfo();
        outputTempArea.appendText(String.valueOf(info.getGemid())+ "'C" +String.valueOf(info.getDescrip()));

        }

}



