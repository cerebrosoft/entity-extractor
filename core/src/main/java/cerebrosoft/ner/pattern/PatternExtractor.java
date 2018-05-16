package cerebrosoft.ner.pattern;

import static cerebrosoft.ner.ExtractorKind.PATTERN;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import cerebrosoft.ner.DefaultEntity;
import cerebrosoft.ner.EntityExtractor;
import cerebrosoft.ner.Extraction;
import cerebrosoft.ner.ExtractionManifest;
import cerebrosoft.ner.ExtractorDefinition;

/**
 * Extracts entities based on a collection of regex patterns
 */
public class PatternExtractor implements EntityExtractor {

    @Override
    public ExtractionManifest performExtraction(ExtractorDefinition def, String text) {
        if (!(def instanceof PatternBook)) throw new IllegalArgumentException();
        List<Extraction> extractions = new ArrayList<>();
        PatternBook book = (PatternBook) def;

        for (PatternDef patternDef : book.getPatterns()) {
            Matcher matcher = patternDef.getPattern().matcher(text);
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                String value = matcher.group();
                DefaultEntity entity = new DefaultEntity.Builder(value).type(patternDef.getType()).build();
                Extraction extraction = new Extraction(entity, start, end - start, PATTERN);
                extractions.add(extraction);
            }
        }
        return new ExtractionManifest(extractions);
    }

}
