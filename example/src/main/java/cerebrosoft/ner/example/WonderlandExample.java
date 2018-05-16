package cerebrosoft.ner.example;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import cerebrosoft.ner.DefaultEntity;
import cerebrosoft.ner.Entity;
import cerebrosoft.ner.book.EntityBook;

public class WonderlandExample extends AbstractExample {

    @Override
    protected void execute() throws Exception {
        URL url = getClass().getResource("/alice30.txt");
        String text = Resources.toString(url, Charsets.UTF_8);
        exec(text, getEntities());
    }

    private EntityBook getEntities() {
        List<Entity> items = new ArrayList<>();
        items.add(new DefaultEntity.Builder("Rabbit").type("Character").property("link", "https://en.wikipedia.org/wiki/White_Rabbit").build());
        items.add(new DefaultEntity.Builder("rabbit").type("Animal").property("link", "https://en.wikipedia.org/wiki/Rabbit").build());
        items.add(new DefaultEntity.Builder("Alice").type("Character").property("link", "https://en.wikipedia.org/wiki/Alice_(Alice%27s_Adventures_in_Wonderland)").build());
        items.add(new DefaultEntity.Builder("Hatter").type("Character").property("link", "https://en.wikipedia.org/wiki/Hatter_(Alice%27s_Adventures_in_Wonderland)").build());
        items.add(new DefaultEntity.Builder("Alice's Adventures in Wonderland").type("Story").property("link", "https://en.wikipedia.org/wiki/Alice%27s_Adventures_in_Wonderland").build());
        items.add(new DefaultEntity.Builder("Lewis Carroll").type("Person").displayString("Charles Lutwidge Dodgson").property("link", "https://en.wikipedia.org/wiki/Lewis_Carroll").build());
        return new EntityBook(items);
    }

    public static void main(String[] args) throws Exception {
        init();
        WonderlandExample example = new WonderlandExample();
        example.execute();
    }

}
