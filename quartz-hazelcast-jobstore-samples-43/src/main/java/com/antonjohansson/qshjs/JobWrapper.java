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

    private static int i = 1;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        int thisId = i++;
        LOG.info("Starting #" + thisId);
        LOG.info("Done executing #" + thisId);
    }
}
