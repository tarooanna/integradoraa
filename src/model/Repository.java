package model;

public class Repository extends Assignment{
    
    private String link;
    private int amountOfDocs;

    public Repository(DevelopmentPhase phase, String link, int archives){
        super(phase);
        this.link = link;
        amountOfDocs = archives;
    }

    @Override
    public String toString() {
        return "Repository [link=" + link + ", amountOfDocs=" + amountOfDocs + "]";
    }

}
