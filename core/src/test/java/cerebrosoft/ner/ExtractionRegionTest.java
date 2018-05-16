package cerebrosoft.ner;

import static cerebrosoft.ner.ExtractorKind.LEXICON;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import cerebrosoft.ner.DefaultEntity;
import cerebrosoft.ner.Extraction;
import cerebrosoft.ner.ExtractionRegion;

public class ExtractionRegionTest {

    private ExtractionRegion region;
    private Extraction extraction1;
    private Extraction extraction2;
    private Extraction extraction3;
    private Extraction extraction4;

    @Before
    public void setUp() {
        region = new ExtractionRegion();

        // example document --> 'this is Bob Smith Perry and Sally'
        extraction1 = new Extraction(new DefaultEntity("Bob Smith"), 8, 9, LEXICON);
        extraction2 = new Extraction(new DefaultEntity("Bob"), 8, 3, LEXICON);
        extraction3 = new Extraction(new DefaultEntity("Smith"), 12, 5, LEXICON);
        extraction4 = new Extraction(new DefaultEntity("Smith Perry"), 12, 11, LEXICON);

        region.add(extraction1);
        region.add(extraction2);
        region.add(extraction3);
        region.add(extraction4);
    }

    @Test
    public void itShouldGetExtractions() {
        assertThat(region.getExtractions()).containsExactly(extraction1, extraction2, extraction3, extraction4);
    }

    @Test
    public void itShouldGetItems() {
        assertThat(region.getItems()).containsExactly(extraction1.getExtractedItem(), extraction2.getExtractedItem(),
            extraction3.getExtractedItem(), extraction4.getExtractedItem());
    }

    @Test
    public void itShouldGetLength() {
        assertThat(region.getLength()).isEqualTo(15);
    }

    @Test
    public void itShouldGetOffset() {
        assertThat(region.getOffset()).isEqualTo(8);
    }

    @Test
    public void itShouldGetLongestExtraction() {
        assertThat(region.getLongestExtraction()).isEqualTo(extraction4);
    }

}
