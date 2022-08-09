package com.dropwizard.todocontroller;

import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.core.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public class todocontrollerConfiguration extends Configuration
{
	@Valid
    @NotNull
    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration()
    {
        return jerseyClient;
    }

    @JsonProperty("jerseyClient")
    public void setJerseyClientConfiguration(JerseyClientConfiguration jerseyClient)
    {
        this.jerseyClient = jerseyClient;
    }
}
