package cerebrosoft.ner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cerebrosoft.ner.book.EntityBook;
import cerebrosoft.ner.pattern.PatternBook;
import cerebrosoft.ner.pattern.PatternDef;

public class AggregateExtractorTest {

    @Test
    public void itShouldExtract() {
        AggregateExtractor extractor = new AggregateExtractor();

        List<Entity> entities = new ArrayList<>();
        entities.add(new DefaultEntity("Larry Smith"));
        EntityBook entityBook = new EntityBook(entities);

        List<PatternDef> patterns = new ArrayList<>();
        patterns.add(new PatternDef("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+", "Email", false));
        PatternBook patternBook = new PatternBook(patterns);

        String text = "On Tuesday, Larry Smith will email the office at office@acme.com to follow up.";
        ExtractionManifest manifest = extractor.extract(text, entityBook, patternBook);
        List<Extraction> extractions = manifest.getExtractions();
        assertThat(extractions).hasSize(2);

        assertThat(extractions.get(0).getExtractedItem().getName()).isEqualTo("Larry Smith");
        assertThat(extractions.get(1).getExtractedItem().getName()).isEqualTo("office@acme.com");
    }

}
