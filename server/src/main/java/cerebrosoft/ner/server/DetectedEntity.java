package cerebrosoft.ner.server;

public class DetectedEntity {
    private final String name;
    private final String matchedText;
    private final String type;
    private final int offset;
    private final int length;

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

}
