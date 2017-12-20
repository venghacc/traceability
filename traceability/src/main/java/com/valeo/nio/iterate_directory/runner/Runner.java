package com.valeo.nio.iterate_directory.runner;

import com.valeo.nio.iterate_directory.Constants;
import com.valeo.nio.iterate_directory.Listing;

public final class Runner {

    private Runner() {
        throw new IllegalStateException(Constants.INSTANTIATION_NOT_ALLOWED);
    }

    public static void main(final String... args) {
    	
        CLIConfigurator.configure(args).ifPresent(cliConfig -> new Listing(cliConfig).list());
    }
    
}
