package cerebrosoft.ner.example;

import java.awt.Desktop;
import java.io.File;
import java.io.StringWriter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.google.common.io.Files;

import cerebrosoft.ner.AggregateExtractor;
import cerebrosoft.ner.ExtractionManifest;
import cerebrosoft.ner.ExtractionRegion;
import cerebrosoft.ner.ExtractorDefinition;

public abstract class AbstractExample {
    
    protected abstract void execute() throws Exception;

    protected void exec(String text, ExtractorDefinition... defs) throws Exception {
        AggregateExtractor extractor = new AggregateExtractor();

        StopWatch stopwatch = StopWatch.createStarted();
        ExtractionManifest manifest = extractor.extract(text, defs);
        String elapsed = stopwatch.getTime(TimeUnit.MILLISECONDS) + " miliseconds";

        int current = 0;
        StringBuilder bldr = new StringBuilder();

        for (ExtractionRegion region : manifest.getRegions()) {
            int offset = region.getOffset();
            int len = region.getLength();
            bldr.append(text.substring(current, offset));
            String tag = processRegion(text.substring(offset, offset + len), region);
            bldr.append(tag);
            current = offset + len;
        }
        bldr.append(text.substring(current));
        String formatted = bldr.toString();

        // create page
        VelocityContext ctx = new VelocityContext();
        ctx.put("content", formatted);
        ctx.put("elapsed", elapsed);
        Template tp = Velocity.getTemplate("templates/page.vm");
        StringWriter sw = new StringWriter();
        tp.merge(ctx, sw);

        // write page to temp file
        File file = File.createTempFile("ner_", ".html");
        file.deleteOnExit();
        Files.write(sw.toString().getBytes(), file);
        Desktop.getDesktop().open(file);
    }

    public String processRegion(String text, ExtractionRegion region) {
        VelocityContext ctx = new VelocityContext();

        ctx.put("entities", region.getItems());
        ctx.put("text", text);
        ctx.put("color", getColor(region.getCommonType()));

        Template tp = Velocity.getTemplate("templates/region.vm");
        StringWriter sw = new StringWriter();
        tp.merge(ctx, sw);
        return sw.toString().trim().replace("\n", "").replace("\r", "");
    }
    
    private String getColor(String type) {
        if (type.equalsIgnoreCase("Person")) return "BLUE";
        if (type.equalsIgnoreCase("City")) return "GREEN";
        if (type.equalsIgnoreCase("Character")) return "PURPLE";
        if (type.equalsIgnoreCase("Phone")) return "DARKKHAKI";
        if (type.equalsIgnoreCase("Email")) return "DARKKHAKI";
        return "brown";
    }
    
    protected static void init() {
        Properties p = new Properties();
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(p);
    }

}
