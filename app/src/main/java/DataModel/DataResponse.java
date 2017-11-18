package DataModel;

/**
 * Created by Hypnos on 11/11/2017.
 */

public class DataResponse {
    private  String response;
    private String vote;
    private int number;

    public DataResponse(String response, String vote, int number) {
        this.response = response;
        this.vote = vote;
        this.number = number;
    }

    public String getResponse() {
        return response;
    }

    public String getVote() {
        return vote;
    }

    public int getNumber() {
        return number;
    }
}
