package co.com.bdb.automation.runner;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.SNIPPET_TYPE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("co.com.bdb.automation.definitions")
@SelectClasspathResource("/features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "co.com.bdb.automation")
@ConfigurationParameter(key = SNIPPET_TYPE_PROPERTY_NAME, value = "camelcase")
@ConfigurationParameter(key = "cumcumber.filter.tags", value = "")


 // Solo ejecuta escenarios con el tag @regresion



public class CucumberTestSuite { }
