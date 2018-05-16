package cerebrosoft.ner;

import static cerebrosoft.ner.ExtractorKind.LEXICON;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import cerebrosoft.ner.DefaultEntity;
import cerebrosoft.ner.Entity;
import cerebrosoft.ner.Extraction;

public class ExtractionTest {

    private Extraction extraction;
    private Entity entity;

    @Before
    public void setUp() {
        entity = new DefaultEntity("Austin, Texas");
        extraction = new Extraction(entity, 10, 15, LEXICON);
    }

    @Test
    public void itShouldGetOffset() {
        assertThat(extraction.getOffset()).isEqualTo(10);
    }

    @Test
    public void itShouldGetLength() {
        assertThat(extraction.getLength()).isEqualTo(15);
    }

    @Test
    public void itShouldGetEntity() {
        assertThat(extraction.getExtractedItem()).isEqualTo(entity);
    }

    @Test
    public void itShouldOverlap() {
        Entity entity2 = new DefaultEntity("junit");
        assertThat(extraction.intersects(new Extraction(entity2, 10, 15, LEXICON))).isTrue();
        assertThat(extraction.intersects(new Extraction(entity2, 10, 10, LEXICON))).isTrue();
        assertThat(extraction.intersects(new Extraction(entity2, 10, 21, LEXICON))).isTrue();
        assertThat(extraction.intersects(new Extraction(entity2, 9, 16, LEXICON))).isTrue();
        assertThat(extraction.intersects(new Extraction(entity2, 11, 14, LEXICON))).isTrue();
        assertThat(extraction.intersects(new Extraction(entity2, 24, 5, LEXICON))).isTrue();
        assertThat(extraction.intersects(new Extraction(entity2, 5, 6, LEXICON))).isTrue();
        assertThat(extraction.intersects(new Extraction(entity2, 5, 40, LEXICON))).isTrue();
    }

    @Test
    public void itShouldNotOverlap() {
        Entity entity2 = new DefaultEntity("junit");
        assertThat(extraction.intersects(new Extraction(entity2, 4, 5, LEXICON))).isFalse();
        assertThat(extraction.intersects(new Extraction(entity2, 26, 5, LEXICON))).isFalse();
        assertThat(extraction.intersects(new Extraction(entity2, 5, 5, LEXICON))).isFalse();
        assertThat(extraction.intersects(new Extraction(entity2, 25, 5, LEXICON))).isFalse();
    }

    @Test
    public void itShouldCompare() {
        Entity entity2 = new DefaultEntity("junit");
        assertThat(extraction).isGreaterThan(new Extraction(entity2, 9, 5, LEXICON));
        assertThat(extraction).isLessThan(new Extraction(entity2, 11, 5, LEXICON));
        assertThat(extraction).isEqualByComparingTo(new Extraction(entity2, 10, 15, LEXICON));
    }

}
