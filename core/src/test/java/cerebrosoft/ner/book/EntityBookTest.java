package cerebrosoft.ner.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cerebrosoft.ner.DefaultEntity;
import cerebrosoft.ner.Entity;

public class EntityBookTest {

    @Test
    public void itShouldConstruct() {
        List<Entity> items = new ArrayList<>();

        DefaultEntity john = new DefaultEntity("John T. Doe");
        DefaultEntity acme = new DefaultEntity("Acme Research Co.");

        items.add(john);
        items.add(acme);
        EntityBook book = new EntityBook(items);
        List<TokenizedEntity> result = book.getTokenizedEntities();
        assertThat(result).hasSize(2);

        TokenizedEntity tJohn = result.get(0);
        assertThat(tJohn.getTokens()).containsOnly("john", "t", "doe");
        assertThat(tJohn.getEntity()).isEqualTo(john);

        TokenizedEntity tAcme = result.get(1);
        assertThat(tAcme.getTokens()).containsOnly("acme", "research", "co");
        assertThat(tAcme.getEntity()).isEqualTo(acme);
    }

}
