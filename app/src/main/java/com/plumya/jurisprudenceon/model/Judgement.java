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

    public String decisionInit;

    public String decision;

    public String justification;

    public String rule;

    public String attachement;

    public Judgement(ParseObject judgement) {
        this.judgementDate = judgement.getString("judgementDate");
        this.bench = judgement.getString("bench");
        this.courtRoom = judgement.getString("courtRoom");
        this.signature = judgement.getString("signature");
        this.issue = judgement.getString("issue");
        this.decisionInit = judgement.getString("decisionInit");
        this.decision = judgement.getString("decision");
        this.justification = judgement.getString("justification");
        this.rule = judgement.getString("rule");
        this.attachement = judgement.getString("attachement");
    }
}
