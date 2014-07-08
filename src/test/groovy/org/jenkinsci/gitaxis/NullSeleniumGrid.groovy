package org.jenkinsci.gitaxis

import spock.lang.Specification

class NullSeleniumGrid extends Specification{

    def 'noServer'(){
        when:
        def sel = new Selenium(Selenium.load("null://"), SeleniumCapability.class)
        then:
        thrown(SeleniumException)
    }
}