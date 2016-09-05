# 43

Sample project to simulate the problem mentioned in this issue: [FlavioF/quartz-scheduler-hazelcast-jobstore#43](https://github.com/FlavioF/quartz-scheduler-hazelcast-jobstore/issues/43)


## Running

```shell
cd quartz-hazelcast-jobstore-samples-43
mvn install
cd target
java -jar quartz-hazelcast-jobstore-samples-43-0.0.1-SNAPSHOT.jar runner (in terminal window #1)
java -jar quartz-hazelcast-jobstore-samples-43-0.0.1-SNAPSHOT.jar runner (in terminal window #2)
java -jar quartz-hazelcast-jobstore-samples-43-0.0.1-SNAPSHOT.jar scheduler (in terminal window #3)
kill the runner in terminal #1 by pressing [Enter]
java -jar quartz-hazelcast-jobstore-samples-43-0.0.1-SNAPSHOT.jar runner (in terminal window #1)
the job should now be running in terminal window #2
java -jar quartz-hazelcast-jobstore-samples-43-0.0.1-SNAPSHOT.jar scheduler (in terminal window #3)
the job is now running in both terminal windows...
```


## Problem

The problem is that when any changes is made to my jobs, I want to re-schedule them. I remove any existing triggers, then I schedule the job again (with any changes applied). However, it seems that the job isn't really unscheduled. The job will run on both nodes.

