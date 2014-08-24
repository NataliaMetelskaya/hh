package ru.hh.test.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hh.test.utils.Config;
import ru.hh.test.utils.XmlParser;

/**
 * @author dzianis_shkindzerau
 */
public abstract class BaseSteps {

    private static final Logger LOG = LoggerFactory.getLogger(BaseSteps.class);

    @Autowired
    protected Config config;

    @Autowired
    protected XmlParser xmlParser;

}
