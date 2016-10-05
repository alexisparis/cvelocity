package org.blackdog.cv;

import org.kohsuke.args4j.Option;

import java.io.File;

/**
 * configuration items that can be changed for this monitoring tool
 * according to the commands args
 * <p>
 * Created by alexisparis on 22/09/15.
 */
public class Options {

    @Option(name = "-help", usage = "print help", required = false)
    private boolean help = false;

    @Option(name = "-template", usage = "template", required = true)
    private File template = null;

    @Option(name = "-output", usage = "output", required = true)
    private File output = null;

    @Option(name = "-params", usage = "json object parameters", required = true)
    private String parameters = null;

    public boolean isHelp() {
        return help;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    public File getTemplate() {
        return template;
    }

    public void setTemplate(File template) {
        this.template = template;
    }

    public File getOutput() {
        return output;
    }

    public void setOutput(File output) {
        this.output = output;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "Options{" +
                "help=" + help +
                ", template=" + template.getAbsolutePath() +
                ", output=" + output.getAbsolutePath() +
                ", parameters='" + parameters + '\'' +
                '}';
    }
}

