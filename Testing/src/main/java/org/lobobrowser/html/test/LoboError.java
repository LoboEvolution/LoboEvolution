package org.lobobrowser.html.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoboError {

	private final static Logger log = LogManager.getLogger(LoboError.class);

	public static void main(String[] args) {
		log.info("error1");
		log.debug("error2");
		log.error("error3");
		log.warn("error4");
	}
}