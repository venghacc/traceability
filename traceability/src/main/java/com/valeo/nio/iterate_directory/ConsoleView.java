package com.valeo.nio.iterate_directory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.valeo.nio.iterate_directory.runner.CLIConfig;

final class ConsoleView {

    private final boolean verbose;
    public List<String> listOfFilePath;

    ConsoleView(final CLIConfig config) {
        this.verbose = config.isVerbose();
        this.listOfFilePath = new ArrayList<String>();
    }

    void render(final Directory directory) {
        render(directory, StringUtils.EMPTY);
    }

    private void render(final Directory directory, final String padding) {
        assert !Objects.isNull(directory) && !StringUtils.isNotEmpty(padding);

        //System.out.println(padding + " d -- " + enrichContent(directory));

        directory.getFileChildren().stream().forEach(file -> render(file, padding + "\t"));
        directory.getDirectoryChildren().stream().forEach(dir -> render(dir, padding + "\t"));
    }

    private void render(final File file, final String padding) {
        //System.out.println("here>>>>>>>>>");
    	assert !Objects.isNull(file) && !StringUtils.isNotEmpty(padding);

        //System.out.println(padding + " f -- " + enrichContent(file));
        //System.out.println("file==>"+file.getPath());
        listOfFilePath.add(file.getPath().toString());
    }

    private String enrichContent(final Directory directory) {
        assert !Objects.isNull(directory);

        try {
            return this.verbose ? directory.getCreated().toString() + " : " + directory.getModified() + " : " + directory.getSize() + " " + directory.getName()
                    : directory.getName();
        } catch (IOException e) {
            return this.verbose ? "E " + directory.getName() : directory.getName();
        }
    }

    private String enrichContent(final File file) {
        assert !Objects.isNull(file);

        try {
            return this.verbose ? file.getCreated() + " : " + file.getModified() + " : " + file.getSize() + " " + file.getName() : file.getName();
        } catch (IOException e) {
            return this.verbose ? "E " + file.getName() : file.getName();
        }
    }
}
