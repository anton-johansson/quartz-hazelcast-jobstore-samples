package com.antonjohansson.qshjs;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.impl.StdSchedulerFactory.AUTO_GENERATE_INSTANCE_ID;
import static org.quartz.impl.StdSchedulerFactory.PROP_JOB_STORE_CLASS;
import static org.quartz.impl.StdSchedulerFactory.PROP_JOB_STORE_PREFIX;
import static org.quartz.impl.StdSchedulerFactory.PROP_SCHED_INSTANCE_ID;
import static org.quartz.impl.StdSchedulerFactory.PROP_SCHED_INSTANCE_ID_GENERATOR_CLASS;
import static org.quartz.impl.StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME;
import static org.quartz.impl.StdSchedulerFactory.PROP_SCHED_JMX_EXPORT;
import static org.quartz.impl.StdSchedulerFactory.PROP_THREAD_POOL_CLASS;
import static org.quartz.impl.StdSchedulerFactory.PROP_THREAD_POOL_PREFIX;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.simpl.SimpleThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bikeemotion.quartz.jobstore.hazelcast.HazelcastJobStore;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * Entry point of the application.
 */
public class EntryPoint
{
    private static final Logger LOG = LoggerFactory.getLogger(EntryPoint.class);
    private static final int INTERVAL = 10000;

    private HazelcastInstance hazelcast;
    private Scheduler scheduler;

    /**
     * Entry point.
     */
    public static void main(String[] args) throws SchedulerException
    {
        EntryPoint entryPoint = new EntryPoint();
        entryPoint.run();
        entryPoint.hold();
        entryPoint.end();
    }

    private void run() throws SchedulerException
    {
        startHazelcast();
        startScheduler();
        scheduleJob();
    }

    private void scheduleJob() throws SchedulerException
    {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobTypeId", 1);
        jobDataMap.put("runAs", 1);
        jobDataMap.put("parameters", new HashMap<>());

        MyJobDetail detail = new MyJobDetail();
        detail.setInstance_id("my-instance-id");
        detail.setJobConfigId(1);
        detail.setConfigName("TestJob");
        detail.setJobDataMap(jobDataMap);

        SimpleTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("1", "my-instance-id")
                .startNow()
                .withSchedule(simpleSchedule()
                        .repeatForever()
                        .withIntervalInMilliseconds(INTERVAL)
                        .withMisfireHandlingInstructionNextWithRemainingCount())
                .build();

        scheduler.unscheduleJob(new TriggerKey("1", "my-instance-id"));
        scheduler.scheduleJob(detail, trigger);
    }

    private void hold()
    {
        LOG.info("Press [Enter] at any time to stop...");
        try
        {
            System.in.read();
        }
        catch (IOException e)
        {
        }
    }

    private void end() throws SchedulerException
    {
        scheduler.shutdown();
        hazelcast.shutdown();
    }

    private void startHazelcast()
    {
        Config config = new Config("AntonJohansson");
        hazelcast = Hazelcast.newHazelcastInstance(config);
    }

    private void startScheduler() throws SchedulerException
    {
        HazelcastJobStore.setHazelcastClient(hazelcast);
        scheduler = configureScheduler();
        scheduler.start();
    }

    private Scheduler configureScheduler() throws SchedulerException
    {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = getProperties();
        try
        {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            StdSchedulerFactory factory = new StdSchedulerFactory(properties);
            Scheduler scheduler = factory.getScheduler();
            return scheduler;
        }
        finally
        {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
    }

    private Properties getProperties()
    {
        Properties properties = new Properties();
        properties.setProperty(PROP_SCHED_INSTANCE_NAME, "AntonJohanssonSchedulerInstance");
        properties.setProperty(PROP_SCHED_INSTANCE_ID, AUTO_GENERATE_INSTANCE_ID);
        properties.setProperty(PROP_SCHED_INSTANCE_ID_GENERATOR_CLASS, QuartzInstanceIdGenerator.class.getName());
        properties.setProperty(PROP_JOB_STORE_CLASS, HazelcastJobStore.class.getName());
        properties.setProperty(PROP_JOB_STORE_PREFIX + ".misfireThreshold", "60000");
        properties.setProperty(PROP_JOB_STORE_PREFIX + ".shutdownHazelcastOnShutdown", "false");
        properties.setProperty(PROP_THREAD_POOL_CLASS, SimpleThreadPool.class.getName());
        properties.setProperty(PROP_THREAD_POOL_PREFIX + ".threadCount", "10");
        properties.setProperty(PROP_THREAD_POOL_PREFIX + ".threadPriority", "5");
        properties.setProperty(PROP_THREAD_POOL_PREFIX + ".threadsInheritContextClassLoaderOfInitializingThread", "true");
        properties.setProperty(PROP_SCHED_JMX_EXPORT, "true");
        return properties;
    }
}
