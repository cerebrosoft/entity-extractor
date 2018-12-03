package cerebrosoft.ner.server;

public class DetectedEntity {
    private final String name;
    private final String matchedText;
    private final String type;
    private final int start;
    private final int offset;

    public DetectedEntity(String name, String matchedText, String type, int start, int offset) {
        this.name = name;
        this.matchedText = matchedText;
        this.type = type;
        this.start = start;
        this.offset = offset;
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

    public int getStart() {
        return start;
    }

    public int getOffset() {
        return offset;
    }

}
