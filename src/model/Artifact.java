package model;

public class Artifact extends Assignment{

    private ArtifactType type;

    public Artifact(ArtifactType type, DevelopmentPhase phase){
        super(phase);
        this.type = type;
    }

    @Override
    public String toString() {
        return "Artifact [type=" + type + "]";
    }
    
}
