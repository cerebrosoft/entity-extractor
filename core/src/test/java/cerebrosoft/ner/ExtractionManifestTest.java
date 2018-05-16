package cerebrosoft.ner;

import static cerebrosoft.ner.ExtractorKind.PATTERN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cerebrosoft.ner.DefaultEntity;
import cerebrosoft.ner.Extraction;
import cerebrosoft.ner.ExtractionManifest;
import cerebrosoft.ner.ExtractionRegion;

public class ExtractionManifestTest {

    private ExtractionManifest manifest;

    private Extraction extraction1;
    private Extraction extraction2;
    private Extraction extraction2b;
    private Extraction extraction3;
    private Extraction extraction4;
    private Extraction extraction5;

    @Before
    public void setUp() {
        // example document --> 'this is Bob Smith Perry and Sally'
        extraction1 = new Extraction(new DefaultEntity("Bob Smith"), 8, 9, PATTERN);
        extraction2 = new Extraction(new DefaultEntity("Bob"), 8, 3, PATTERN);
        extraction2b = new Extraction(new DefaultEntity("Bob"), 8, 3, PATTERN);
        extraction3 = new Extraction(new DefaultEntity("Smith"), 12, 5, PATTERN);
        extraction4 = new Extraction(new DefaultEntity("Smith Perry"), 12, 11, PATTERN);
        extraction5 = new Extraction(new DefaultEntity("Sally"), 28, 5, PATTERN);

        manifest = new ExtractionManifest(newArrayList(extraction1, extraction2, extraction2b, extraction3, extraction4, extraction5));
    }

    @Test
    public void itShouldGetExtractions() {
        assertThat(manifest.getExtractions()).containsOnly(extraction1, extraction2, extraction2b, extraction3, extraction4, extraction5);
    }

    @Test
    public void itShouldGetNonintersectingExtractions() {
        assertThat(manifest.getNonIntersectingExtractions()).containsOnly(extraction4, extraction5);
    }

    @Test
    public void itShouldGetRegions() {
        List<ExtractionRegion> regions = manifest.getRegions();
        assertThat(regions).hasSize(2);
        ExtractionRegion region1 = regions.get(0);
        assertThat(region1.getExtractions()).containsOnly(extraction1, extraction2, extraction2b, extraction3, extraction4);
        assertThat(region1.getLength()).isEqualTo(15);
        assertThat(region1.getOffset()).isEqualTo(8);

        ExtractionRegion region2 = regions.get(1);
        assertThat(region2.getExtractions()).containsOnly(extraction5);
        assertThat(region2.getLength()).isEqualTo(5);
        assertThat(region2.getOffset()).isEqualTo(28);
    }

}
