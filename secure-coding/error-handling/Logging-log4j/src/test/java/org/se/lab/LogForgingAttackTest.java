package org.se.lab;

import org.apache.log4j.Logger;
import org.junit.Test;


/*
 * The integrity of log files is very important because the data in log 
 * files tell us exactly what happened in our system and when.
 * 
 * Log forging vulnerabilities allow an attacker to insert arbitrary log
 * file entries into your server logs and corrupt the integrity of log
 * records.
 */

public class LogForgingAttackTest
{
	private final Logger logger = Logger.getLogger(LogForgingAttackTest.class);

	@Test
	public void testLogForging()
	{
		log("homer");
		log("bart\n 2099-01-01 00:00:00,000 [main] INFO HACK!!!!");
		log("lisa");
	}


    @Test
    public void testLogEncoding()
    {
        log(encodeLogMessage("homer"));
        log(encodeLogMessage("bart\n 2099-01-01 00:00:00,000 [main] INFO HACK!!!!"));
        log(encodeLogMessage("lisa"));
    }


	protected void log(String name)
	{
		logger.info("Parameter name = " + name);
	}


	private String encodeLogMessage(String msg)
    {
        int len = msg.length();
        StringBuilder html = new StringBuilder();
        for(int i=0; i<len; i++)
        {
            char c = msg.charAt(i);
            char encoding;
            switch(c)
            {
                case '\r': encoding = '_'; break;
                case '\n': encoding = '_'; break;
                case '\t': encoding = '_'; break;
                default: encoding = c;
            }
            html.append(encoding);
        }
        return html.toString();
    }
}
