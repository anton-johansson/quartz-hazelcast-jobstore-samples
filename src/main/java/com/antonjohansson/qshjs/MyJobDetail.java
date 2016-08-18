package com.antonjohansson.qshjs;

import static java.lang.String.valueOf;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;

/**
 * {@link JobDetail} implementation that maps to the {@code JobDetail} table.
 */
@SuppressWarnings("serial")
public class MyJobDetail implements JobDetail
{
    private String instance_id = "";
    private int jobConfigId;
    private String configName = "";
    private JobDataMap jobDataMap = new JobDataMap();

    /**
     * Constructs a new, empty {@link MyJobDetail}.
     */
    public MyJobDetail()
    {
    }

    /**
     * Constructs a new {@link MyJobDetail} by copying the data from another instance.
     *
     * @param source The source instance to get values from.
     */
    public MyJobDetail(MyJobDetail source)
    {
        this.instance_id = source.instance_id;
        this.jobConfigId = source.jobConfigId;
        this.configName = source.configName;
        this.jobDataMap = new JobDataMap(source.jobDataMap);
    }

    public String getInstance_id()
    {
        return instance_id;
    }

    public void setInstance_id(String instance_id)
    {
        this.instance_id = instance_id;
    }

    public int getJobConfigId()
    {
        return jobConfigId;
    }

    public void setJobConfigId(int jobConfigurationId)
    {
        this.jobConfigId = jobConfigurationId;
    }

    public String getConfigName()
    {
        return configName;
    }

    public void setConfigName(String configurationName)
    {
        this.configName = configurationName;
    }

    public void setJobDataMap(JobDataMap jobDataMap)
    {
        this.jobDataMap = jobDataMap;
    }

    @Override
    public JobKey getKey()
    {
        return new JobKey(valueOf(jobConfigId), instance_id);
    }

    @Override
    public String getDescription()
    {
        return configName;
    }

    @Override
    public Class<? extends Job> getJobClass()
    {
        return JobWrapper.class;
    }

    @Override
    public JobDataMap getJobDataMap()
    {
        return jobDataMap;
    }

    @Override
    public boolean isDurable()
    {
        return true;
    }

    @Override
    public boolean isPersistJobDataAfterExecution()
    {
        return false;
    }

    @Override
    public boolean isConcurrentExectionDisallowed()
    {
        return false;
    }

    @Override
    public boolean requestsRecovery()
    {
        return false;
    }

    @Override
    public JobBuilder getJobBuilder()
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    //CSOFF
    @Override
    public MyJobDetail clone()
    {
        return new MyJobDetail(this);
    }
    //CSON
}
