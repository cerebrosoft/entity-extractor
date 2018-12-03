package cerebrosoft.ner.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cerebrosoft.ner.ExtractorDefinition;

@RestController
@CrossOrigin
public class ExtractionController {
    @Autowired
    private ExtractorService extractorService;
    @Autowired
    private LexiconService lexiconService;

    @PostMapping("/extract")
    public List<DetectedEntity> createMessage(@RequestBody ExtractionRequest request) throws Exception {
        List<ExtractorDefinition> defs = new ArrayList<>();
        for (String defName : request.getExtractorDefs()) {
            if (defName.toLowerCase().endsWith(".entities")) {
                defs.add(lexiconService.getEntities(defName));
            }
            else if (defName.toLowerCase().endsWith(".patterns")) {
                defs.add(lexiconService.getPatterns(defName));
            }
        }
        List<DetectedEntity> result = extractorService.extract(request.getText(), defs);
        return result;
    }

}