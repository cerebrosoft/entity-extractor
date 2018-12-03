package cerebrosoft.ner.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cerebrosoft.ner.AggregateExtractor;
import cerebrosoft.ner.DefaultEntity;
import cerebrosoft.ner.Entity;
import cerebrosoft.ner.Extraction;
import cerebrosoft.ner.ExtractionManifest;
import cerebrosoft.ner.ExtractorDefinition;

@Service
public class ExtractorService {

    public List<DetectedEntity> extract(String text, List<ExtractorDefinition> defs) {
        List<DetectedEntity> entities = new ArrayList<>();
        AggregateExtractor extractor = new AggregateExtractor();
        ExtractionManifest manifest = extractor.extract(text, defs.toArray(new ExtractorDefinition[] {}));
        for (Extraction extraction : manifest.getExtractions()) {
            Entity item = extraction.getExtractedItem();
            String overrideName = null;
            if (item instanceof DefaultEntity) {
                overrideName = ((DefaultEntity) item).getDisplayString();
            }
            if (StringUtils.isNotBlank(overrideName)) {
                entities.add(new DetectedEntity(overrideName, item.getName(), item.getType(), extraction.getOffset(), extraction.getLength()));
            }
            else {
                entities.add(new DetectedEntity(item.getName(), item.getName(), item.getType(), extraction.getOffset(), extraction.getLength()));
            }
        }
        return entities;
    }

}
