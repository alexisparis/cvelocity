package org.blackdog.cv;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Map;

/**
 *
 * Main class for the monitoring application for tango
 *
 * Created by alexisparis on 22/09/15.
 */
public class Runner {

    /**
     * logger
     */
    static Logger LOGGER = Logger.getLogger(Runner.class.getName());

    public static void main(String[] args) {

        CmdLineParser parser = null;
        Options options = null;

        try {
            // parse options
            options = new Options();
            parser = new CmdLineParser(options);

            parser.parseArgument(args);

            if (options.isHelp()) {
                throw new CmdLineException(parser, "help display requested", null);
            }

            velocify(options);

            LOGGER.info("###############################");
        } catch (CmdLineException e) {
            LOGGER.error(e.getMessage(), e);
            System.err.println("java -jar XXX.jar [options...] arguments...");
            parser.printUsage(System.err);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        System.exit(0);
    }

    public static void velocify(Options options) throws Exception {
        LOGGER.info("velocifying with options : " + options.toString());
        // parse the json object
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, Object> myMap = gson.fromJson(options.getParameters(), type);

        // load template
        /*  first, get and initialize an engine  */
        VelocityEngine ve = new VelocityEngine();
        ve.init();
        /*  next, get the Template  */
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
        ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, new File(options.getTemplate().getAbsolutePath()).getParent());
        Template t = ve.getTemplate(options.getTemplate().getName());
        /*  create a context and add data */
        VelocityContext context = new VelocityContext();
        for(Map.Entry<String, Object> currentEntry : myMap.entrySet()) {
            context.put(currentEntry.getKey(), currentEntry.getValue());
        }


        /* now render the template into a StringWriter */
        Writer writer = new FileWriter(options.getOutput());
        t.merge(context, writer);
        writer.close();
    }
}