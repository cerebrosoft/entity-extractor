package cerebrosoft.ner.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;

import cerebrosoft.ner.DefaultEntity;
import cerebrosoft.ner.Entity;
import cerebrosoft.ner.book.EntityBook;
import cerebrosoft.ner.pattern.PatternBook;
import cerebrosoft.ner.pattern.PatternDef;

@Service
public class LexiconService {
    @Value("${lexicon.path}")
    private String lexiconPath;

    public EntityBook getEntities(String name) throws Exception {
        // name, entity, type
        List<Entity> items = new ArrayList<>();
        List<String[]> rows = readCsv(new File(lexiconPath, name));
        for (String[] row : rows) {
            items.add(new DefaultEntity.Builder(row[0]).displayString(row[1]).type(row[2]).build());
        }
        return new EntityBook(items);
    }

    public PatternBook getPatterns(String name) throws Exception {
        // pattern, type
        List<PatternDef> patterns = new ArrayList<>();
        List<String[]> rows = readCsv(new File(lexiconPath, name));
        for (String[] row : rows) {
            patterns.add(new PatternDef(row[0], row[1], false));
        }
        return new PatternBook(patterns);
    }

    private List<String[]> readCsv(File file) throws Exception {
        List<String[]> rows = new ArrayList<>();
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf8");
        try (CSVReader reader = new CSVReader(isr)) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                rows.add(line);
            }
        }
        return rows;
    }
}
