package com.dropwizard.todocontroller;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class todocontrollerApplication extends Application<todocontrollerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new todocontrollerApplication().run(args);
    }

    @Override
    public String getName() {
        return "todocontroller";
    }

    @Override
    public void initialize(final Bootstrap<todocontrollerConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final todocontrollerConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
