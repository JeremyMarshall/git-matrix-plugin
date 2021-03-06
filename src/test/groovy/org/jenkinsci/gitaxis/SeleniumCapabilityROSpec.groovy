package org.jenkinsci.gitaxis

import spock.lang.Specification

class SeleniumCapabilityROSpec extends Specification{

    def 'Create Param'() {

        def selCap = new SeleniumCapabilityRO('Browser', 'Platform', 'Version')

        expect:
        selCap.getBrowserName().matches('Browser')
        selCap.getBrowserVersion().matches('Version')
        selCap.getPlatformName().matches('Platform')
    }
}
