package de.unidue.inf.is.domain;

public class UserComment {

    private int commentId;
    private int advertId;
    private String username;

    public UserComment(int commentId, String username, int advertId){
        this.commentId = commentId;
        this.username = username;
        this.advertId = advertId;
    }

    public int getCommentId(){ return commentId;}

    public int getAdvertId(){ return advertId;}

    public String getUsername(){ return username;}
}
