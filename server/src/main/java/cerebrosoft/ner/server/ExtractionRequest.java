package cerebrosoft.ner.server;

import java.util.List;

public class ExtractionRequest {
    private String text;
    private List<String> extractorDefs;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getExtractorDefs() {
        return extractorDefs;
    }

    public void setExtractorDefs(List<String> extractorDefs) {
        this.extractorDefs = extractorDefs;
    }

}
