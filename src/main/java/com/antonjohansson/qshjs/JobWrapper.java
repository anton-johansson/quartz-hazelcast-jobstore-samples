package com.antonjohansson.qshjs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrapper around Quartz' {@link Job} interface.
 */
public class JobWrapper implements Job
{
    private static final Logger LOG = LoggerFactory.getLogger(JobWrapper.class);
    private static final int FIVE = 5;
    private static final int SLEEP_TIME = 12000;

    private static int i = 1;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        int thisId = i++;

        LOG.info("Starting #" + thisId);
        if (thisId % FIVE == 0)
        {
            try
            {
                Thread.sleep(SLEEP_TIME);
            }
            catch (InterruptedException e)
            {
            }
        }

        LOG.info("Done executing #" + thisId);
    }
}
