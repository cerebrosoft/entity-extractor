package cerebrosoft.ner.pattern;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cerebrosoft.ner.Extraction;
import cerebrosoft.ner.ExtractionManifest;

public class PatternExtractorTest {

    @Test
    public void itShouldExtract() throws Exception {
        PatternExtractor extractor = new PatternExtractor();
        List<PatternDef> patterns = new ArrayList<>();
        patterns.add(new PatternDef("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+", "Email", false));
        PatternBook book = new PatternBook(patterns);
        String text = "Contact us at fred@example.com or sally@example.com";
        ExtractionManifest manifest = extractor.performExtraction(book, text);

        List<Extraction> extractions = manifest.getExtractions();
        assertThat(extractions).hasSize(2);

        assertThat(extractions.get(0).getOffset()).isEqualTo(14);
        assertThat(extractions.get(0).getLength()).isEqualTo(16);
        assertThat(extractions.get(0).getExtractedItem().getName()).isEqualTo("fred@example.com");

        assertThat(extractions.get(1).getOffset()).isEqualTo(34);
        assertThat(extractions.get(1).getLength()).isEqualTo(17);
        assertThat(extractions.get(1).getExtractedItem().getName()).isEqualTo("sally@example.com");
    }

    @Test
    public void itShouldBeCaseSensitive() throws Exception {
        PatternExtractor extractor = new PatternExtractor();
        List<PatternDef> patterns = new ArrayList<>();
        patterns.add(new PatternDef("Sally Smith", "Person", false));
        PatternBook book = new PatternBook(patterns);

        String text = "Contact Sally Smith for details.";
        ExtractionManifest manifest = extractor.performExtraction(book, text);
        assertThat(manifest.getExtractions()).hasSize(1);

        patterns = new ArrayList<>();
        patterns.add(new PatternDef("sally smith", "Person", false));
        book = new PatternBook(patterns);
        manifest = extractor.performExtraction(book, text);
        List<Extraction> extractions = manifest.getExtractions();
        assertThat(extractions).isEmpty();
    }
    
    @Test
    public void itShouldIgnoreCase() throws Exception {
        PatternExtractor extractor = new PatternExtractor();
        List<PatternDef> patterns = new ArrayList<>();
        patterns.add(new PatternDef("Sally Smith", "Person", true));
        PatternBook book = new PatternBook(patterns);

        String text = "Contact sally smith for details.";
        ExtractionManifest manifest = extractor.performExtraction(book, text);
        assertThat(manifest.getExtractions()).hasSize(1);
    }
}
