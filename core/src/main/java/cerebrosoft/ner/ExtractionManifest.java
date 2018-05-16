package cerebrosoft.ner;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The result of an entity extraction job
 */
public class ExtractionManifest {

    private final List<Extraction> extractions;
    private List<ExtractionRegion> regions;

    /**
     * Constructor
     * 
     * @param extractions the extractions from an entity extraction job
     */
    public ExtractionManifest(List<Extraction> extractions) {
        this.extractions = checkNotNull(extractions);
        Collections.sort(extractions);
    }

    /**
     * @return the extraction regions from an extraction job
     */
    public List<ExtractionRegion> getRegions() {
        if (regions != null) return regions;
        regions = new ArrayList<>();
        ExtractionRegion lastRegion = null;

        for (Extraction extraction : extractions) {
            if (lastRegion != null && lastRegion.intersects(extraction)) {
                lastRegion.add(extraction);
            }
            else {
                lastRegion = new ExtractionRegion();
                lastRegion.add(extraction);
                regions.add(lastRegion);
            }
        }
        return regions;
    }

    /**
     * @return the extractions
     */
    public List<Extraction> getExtractions() {
        return extractions;
    }

    /**
     * @return the extractions, without overlaps. (the longest extraction per region is returned.)
     */
    public List<Extraction> getNonIntersectingExtractions() {
        List<Extraction> revisedItems = new ArrayList<>();
        for (ExtractionRegion region : getRegions()) {
            revisedItems.add(region.getLongestExtraction());
        }
        return revisedItems;
    }

    /**
     * Merges multiple manifests into one
     * 
     * @param manifests the manifests to merge
     * @return the merged manifest
     */
    public static ExtractionManifest merge(List<ExtractionManifest> manifests) {
        List<Extraction> extractions = new ArrayList<>();
        for (ExtractionManifest manifest : manifests) {
            extractions.addAll(manifest.getExtractions());
        }
        return new ExtractionManifest(extractions);
    }

}
