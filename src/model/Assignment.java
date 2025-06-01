package model;

import java.io.Serializable;
import java.util.UUID;

public class Assignment implements Identifiable, Serializable{

    private DevelopmentPhase phase;
    private String code;
    
    public Assignment(DevelopmentPhase phase){
        this.phase = phase;
        this.code = generateCode();
    }

    public String generateCode(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }

    @Override
    public String toString() {
        return "Assignment [phase=" + phase + ", code=" + code + "]";
    }

    public String getCode() {
        return code;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Assignment other = (Assignment) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        return true;
    }
    
}
