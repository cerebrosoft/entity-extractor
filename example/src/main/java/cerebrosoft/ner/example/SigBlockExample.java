package cerebrosoft.ner.example;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import cerebrosoft.ner.DefaultEntity;
import cerebrosoft.ner.Entity;
import cerebrosoft.ner.book.EntityBook;
import cerebrosoft.ner.pattern.PatternBook;
import cerebrosoft.ner.pattern.PatternDef;

public class SigBlockExample extends AbstractExample {

    @Override
    protected void execute() throws Exception {
        URL url = getClass().getResource("/signature.txt");
        String text = Resources.toString(url, Charsets.UTF_8);
        exec(text, getEntities(), getPatterns());
    }

    private EntityBook getEntities() {
        List<Entity> items = new ArrayList<>();
        items.add(new DefaultEntity.Builder("John Smith").type("Person").build());
        items.add(new DefaultEntity.Builder("Martin Luther King Jr").type("Person").build());
        items.add(new DefaultEntity.Builder("Acme University").type("Organization").build());
        items.add(new DefaultEntity.Builder("Waltham").type("City").build());
        
        return new EntityBook(items);
    }
    
    private PatternBook getPatterns() {
        List<PatternDef> patterns = new ArrayList<>();
        patterns.add(new PatternDef("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+", "Email", false));
        patterns.add(new PatternDef("\\b\\d{3}[.]?\\d{3}[.]?\\d{4}\\b", "Phone", false));

        return new PatternBook(patterns);
    }

    public static void main(String[] args) throws Exception {
        init();
        SigBlockExample example = new SigBlockExample();
        example.execute();
    }

}
