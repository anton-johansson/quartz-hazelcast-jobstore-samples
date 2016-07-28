# quartz-scheduler-hazelcast-jobstore-sample

Sample project to simulate the problem mentioned in this issue: FlavioF/quartz-scheduler-hazelcast-jobstore#36


## Running

```shell
mvn install
java -jar target/quartz-scheduler-hazelcast-jobstore-sample-0.0.1-SNAPSHOT.jar 
```


## Sample output

```
2016-07-28 14:26:57.857 INFO  StdSchedulerFactory:1339 - Quartz scheduler 'AntonJohanssonSchedulerInstance' initialized from an externally provided properties instance.
2016-07-28 14:26:57.857 INFO  StdSchedulerFactory:1343 - Quartz scheduler version: 2.2.1
2016-07-28 14:26:57.857 INFO  HazelcastJobStore:135 - Hazelcast Job Store started successfully
2016-07-28 14:26:57.857 INFO  QuartzScheduler:575 - Scheduler AntonJohanssonSchedulerInstance_$_L-2015-208-1.2.3-1469708817738 started.
2016-07-28 14:26:57.891 INFO  EntryPoint:92 - Press [Enter] at any time to stop...
2016-07-28 14:26:57.915 INFO  JobWrapper:19 - Executing
2016-07-28 14:27:53.179 WARN  HazelcastJobStore:850 - Found a lost trigger [TriggerWrapper{trigger=Trigger 'my-instance-id.1':  triggerClass: 'org.quartz.impl.triggers.SimpleTriggerImpl calendar: 'null' misfireInstruction: 4 nextFireTime: Thu Jul 28 14:26:57 CEST 2016, state=ACQUIRED, nextFireTime=1469708817859, acquiredAt=1469708817000}] that must be released at [1469708903173]
2016-07-28 14:27:53.187 INFO  JobWrapper:19 - Executing
2016-07-28 14:28:46.081 WARN  HazelcastJobStore:850 - Found a lost trigger [TriggerWrapper{trigger=Trigger 'my-instance-id.1':  triggerClass: 'org.quartz.impl.triggers.SimpleTriggerImpl calendar: 'null' misfireInstruction: 4 nextFireTime: Thu Jul 28 14:26:57 CEST 2016, state=ACQUIRED, nextFireTime=1469708817859, acquiredAt=1469708873000}] that must be released at [1469708956079]
2016-07-28 14:28:47.863 INFO  JobWrapper:19 - Executing
2016-07-28 14:29:38.811 WARN  HazelcastJobStore:850 - Found a lost trigger [TriggerWrapper{trigger=Trigger 'my-instance-id.1':  triggerClass: 'org.quartz.impl.triggers.SimpleTriggerImpl calendar: 'null' misfireInstruction: 4 nextFireTime: Thu Jul 28 14:28:47 CEST 2016, state=ACQUIRED, nextFireTime=1469708927859, acquiredAt=1469708926000}] that must be released at [1469709008809]
2016-07-28 14:29:38.820 INFO  JobWrapper:19 - Executing
```


## Problem

The problem is that the triggers aren't firing when they mean to. We also get a bunch of warnings about lost triggers, which is probably the cause of it.
