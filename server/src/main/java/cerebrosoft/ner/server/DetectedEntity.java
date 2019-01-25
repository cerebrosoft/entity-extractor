package cerebrosoft.ner.server;

public class DetectedEntity {
    private  String name;
    private  String matchedText;
    private  String type;
    private  int offset;
    private  int length;
    
    public DetectedEntity() {};

    public DetectedEntity(String name, String matchedText, String type, int offset, int length) {
        this.name = name;
        this.matchedText = matchedText;
        this.type = type;
        this.offset = offset;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public String getMatchedText() {
        return matchedText;
    }

    public String getType() {
        return type;
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMatchedText(String matchedText) {
        this.matchedText = matchedText;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setLength(int length) {
        this.length = length;
    }
    

}
