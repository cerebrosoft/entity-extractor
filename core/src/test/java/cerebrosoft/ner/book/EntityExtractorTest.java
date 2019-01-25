package cerebrosoft.ner.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cerebrosoft.ner.DefaultEntity;
import cerebrosoft.ner.Entity;
import cerebrosoft.ner.Extraction;
import cerebrosoft.ner.ExtractionManifest;

public class EntityExtractorTest {

    @Test
    public void itShouldExtract() throws Exception {
        String text = "Alice encountered the White Rabbit";

        List<Entity> items = new ArrayList<>();
        items.add(new DefaultEntity("White Rabbit"));
        items.add(new DefaultEntity("rabbit"));
        items.add(new DefaultEntity("rabbit hole"));
        items.add(new DefaultEntity("Alice"));

        EntityBook book = new EntityBook(items);

        LexiconExtractor extractor = new LexiconExtractor();
        ExtractionManifest manifest = extractor.performExtraction(book, text);
        
        List<Extraction> extractions = manifest.getExtractions();
        assertThat(extractions).hasSize(3);

        assertThat(extractions.get(0).getOffset()).isEqualTo(0);
        assertThat(extractions.get(0).getLength()).isEqualTo(5);
        assertThat(extractions.get(0).getExtractedItem().getName()).isEqualTo("Alice");
        
        assertThat(extractions.get(1).getOffset()).isEqualTo(22);
        assertThat(extractions.get(1).getLength()).isEqualTo(12);
        assertThat(extractions.get(1).getExtractedItem().getName()).isEqualTo("White Rabbit");
        
        assertThat(extractions.get(2).getOffset()).isEqualTo(28);
        assertThat(extractions.get(2).getLength()).isEqualTo(6);
        assertThat(extractions.get(2).getExtractedItem().getName()).isEqualTo("rabbit");
    }

}
