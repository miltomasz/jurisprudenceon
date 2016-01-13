package com.plumya.jurisprudenceon.model;

import com.parse.Parse;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by toml on 25.03.15.
 */
public class Judgement implements Serializable {

    public int judgementId;
    public String bench;
    public String courtRoom;
    public String signature;
    public String judgementDate;
    public String issue;
    public String issueUrl;
    public String issueUrlLabel;
    public String decisionInit;
    public String decision;
    public String decisionUrl;
    public String decisionUrlLabel;

    public Judgement(ParseObject judgement) {
        this.judgementDate = judgement.getString("judgementDate");
        this.bench = judgement.getString("bench");
        this.courtRoom = judgement.getString("courtRoom");
        this.signature = judgement.getString("signature");
        this.issue = judgement.getString("issue");
        this.issueUrl = judgement.getString("issueUrl");
        this.issueUrlLabel = judgement.getString("issueUrlLabel");
        this.decisionInit = judgement.getString("decisionInit");
        this.decision = judgement.getString("decision");
        this.decisionUrl = judgement.getString("decisionUrl");
        this.decisionUrlLabel = judgement.getString("decisionUrlLabel");
    }
}
