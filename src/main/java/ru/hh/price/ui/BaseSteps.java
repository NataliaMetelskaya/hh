package ru.hh.price.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hh.price.utils.Config;
import ru.hh.price.utils.XmlParser;

/**
 * @author e3s team
 */
public abstract class BaseSteps {

    private static Logger log = LoggerFactory.getLogger(BaseSteps.class);

    @Autowired
    protected Config config;

    @Autowired
    protected XmlParser xmlParser;

}
