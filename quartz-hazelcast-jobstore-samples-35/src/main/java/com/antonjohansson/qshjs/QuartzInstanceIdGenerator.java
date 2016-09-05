package com.antonjohansson.qshjs;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.quartz.SchedulerException;
import org.quartz.simpl.HostnameInstanceIdGenerator;
import org.quartz.spi.InstanceIdGenerator;

/**
 * Generates Quartz instance ID's by concatenating the host name and the current implementation version.
 *
 * @see HostnameInstanceIdGenerator
 */
public class QuartzInstanceIdGenerator implements InstanceIdGenerator
{
    private static final String IMPLEMENTATION_VERSION = "1.2.3";

    @Override
    public String generateInstanceId() throws SchedulerException
    {
        try
        {
            return InetAddress.getLocalHost().getHostName() + "-" + IMPLEMENTATION_VERSION + "-" + System.currentTimeMillis();
        }
        catch (UnknownHostException e)
        {
            throw new SchedulerException("Couldn't get host name!", e);
        }
    }
}
