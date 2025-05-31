package model;

public class Document extends Assignment{

    private String link;

    public Document(DevelopmentPhase phase, String link){
        super(phase);
        this.link = link;
    }

    @Override
    public String toString() {
        return "Document [link=" + link + "]";
    }
    
}
