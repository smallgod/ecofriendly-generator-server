/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver.scheduler;

import com.namaraka.ggserver.interfaces.SharedAppConfigIF;
import java.io.Serializable;
import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobListener;
import org.quartz.JobKey;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDataMap;
import static org.quartz.TriggerKey.triggerKey;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerKey.triggerKey;

/**
 *
 * @author smallgod
 */
public final class CustomJobScheduler implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(CustomJobScheduler.class);

    private final Scheduler scheduler;
    private final CustomSharedScheduler customSharedScheduler;

    private static final long serialVersionUID = -6798027257124056867L;

    public CustomJobScheduler() {
        this.customSharedScheduler = CustomSharedScheduler.getInstance();
        this.scheduler = customSharedScheduler.getScheduler();
    }

    /**
     * Schedule a JOB, start the JOB and add a JobListener that monitors for
     * events like when the job is finally executed. Before this job is
     * scheduled the method checks to see if this job was previously scheduled
     * and hence exists. If so, the previous will be deleted first before
     * re-scheduling it.
     *
     * @param jobsData
     * @param jobClass
     * @param jobListener
     * @return the Trigger created for this Job
     */
    public Trigger scheduleARepeatJob(JobsData jobsData, Class<? extends Job> jobClass, JobListener jobListener) {

        
        // A repeat Job will not always be an "adFetch Job", so work on logic to change this
        //and make sure it accepts any kind of Job
        String triggerName = jobsData.getTriggerName();
        String jobName = jobsData.getJobName();
        String groupName = jobsData.getGroupName();
        int repeatInterval = jobsData.getRepeatInterval();

        Trigger triggerToFire = createRepeatTrigger(triggerName, groupName, repeatInterval);
        JobDetail jobTodo = prepareJob(jobName, groupName, jobsData, jobClass);

        //JobKey jobKey = jobTodo.getKey();
        try {

            deleteAJob(jobName, groupName);

            scheduler.scheduleJob(jobTodo, triggerToFire);
            scheduler.start();
            scheduler.getListenerManager().addJobListener(jobListener);

            logger.debug("Job Trigger: " + triggerName + ", successfuly added");

        } catch (SchedulerException ex) {
            logger.error("Error linking job to trigger and starting scheduler: " + ex.getMessage());
        }
        return triggerToFire;

    }

    /**
     * Pause a given job
     *
     * @param jobName
     * @param groupName
     * @return
     */
    public boolean pauseAJob(String jobName, String groupName) {

        JobKey jobKey = getJobKey(jobName, groupName);

        boolean isJobPaused = Boolean.TRUE;

        try {

            scheduler.pauseJob(jobKey);

        } catch (SchedulerException ex) {
            logger.error("Error pausing a job: " + ex.getMessage());
            isJobPaused = Boolean.FALSE;
        }
        return isJobPaused;

    }

    public boolean resumeAJob(String jobName, String groupName) {

        JobKey jobKey = getJobKey(jobName, groupName);

        boolean isJobResumed = Boolean.TRUE;

        try {

            scheduler.resumeJob(jobKey);

        } catch (SchedulerException ex) {
            logger.error("Error resuming a job: " + ex.getMessage());
            isJobResumed = Boolean.FALSE;
        }
        return isJobResumed;

    }

    public boolean isJobTriggerPaused(String triggerName) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName);
        Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);

        boolean isPaused = Boolean.FALSE;

        switch (triggerState) {

            case PAUSED:
                isPaused = Boolean.TRUE;
                break;

            case NORMAL:
                isPaused = Boolean.FALSE;
                break;

            default:
                throw new SchedulerException("Trigger is neither in PAUSED nor NORMAL state: " + triggerState.toString());
        }

        return isPaused;
    }

    /**
     * Delete a job using the given jobName and the groupName for this job
     *
     * @param jobName
     * @param groupName
     * @return
     */
    public boolean deleteAJob(String jobName, String groupName) {

        JobKey jobKey = getJobKey(jobName, groupName);

        boolean isJobDeleted = Boolean.TRUE;

        try {

            //scheduler.deleteJob(new JobKey("Job1", "Group1"));
            //check first if this job exits in the scheduler - unschedule the trigger/and job if necessary/redundant
            if (scheduler.checkExists(jobKey)) {
                scheduler.interrupt(jobKey); //interrupt job if running so that we are able to delete it

                //loop through all the triggers having a reference to this job, to unschedule them removes the job from the jobstore
                isJobDeleted = scheduler.deleteJob(jobKey);
                logger.debug("jobKey: " + jobKey + " found and deleted: " + isJobDeleted + ", so that it can be rescheduled");
            }

        } catch (SchedulerException ex) {
            logger.error("Error deleting a job: " + ex.getMessage());
        }
        return isJobDeleted;

    }

    public void scheduleAOneTimeJob(String triggerName, String jobName, String groupName) {

    }

//    private void setupScheduler() {
//
//        Trigger trigger = createTrigger(props.getSchedulers().getRetryfailed().getTriggername().trim());
//        JobDetail job = createJob(props.getSchedulers().getRetryfailed().getJobname().trim());
//        scheduleJob(trigger, job);
//    }
    private SimpleTrigger createRepeatTrigger(String triggerName, String groupName, int repeatInterval) {

        SimpleTrigger trigger = newTrigger()
                .withIdentity(triggerKey(triggerName, groupName))
                //.startNow()
                .startAt(DateBuilder.futureDate(repeatInterval, DateBuilder.IntervalUnit.SECOND))
                .withSchedule(simpleSchedule()
                        .withMisfireHandlingInstructionNextWithRemainingCount()
                        .withIntervalInSeconds(repeatInterval)
                        .repeatForever())
                //.startAt(futureDate(5, TimeUnit.SECONDS))
                .build();

        return trigger;
    }

    /**
     * uses the given jobName and job groupname to retrieve jobkey
     *
     * @param jobName
     * @param groupName
     * @return
     */
    private JobKey getJobKey(String jobName, String groupName) {
        return JobKey.jobKey(jobName, groupName);
    }

    /**
     * Uses the given jobName and default job groupname to retrieve jobkey
     *
     * @param jobName
     * @return jobkey
     */
    private JobKey getJobKey(String jobName) {
        return JobKey.jobKey(jobName);
    }

    private JobDetail prepareJob(String jobName, String jobGroupName, JobsData jobsData, Class<? extends Job> jobToExecute) { //add param for jobdatamap if needed

        JobKey jobKey = getJobKey(jobName, jobGroupName);
        JobDataMap jobData = createJobDataMap(jobName, jobsData);

        JobDetail job = newJob(jobToExecute)
                .withIdentity(jobKey)
                .setJobData(jobData)
                //.usingJobData("jobSays", "Hello World!")
                //.usingJobData("myFloatValue", 3.141f)
                .storeDurably(Boolean.FALSE) //deleted automatically when there are no longer active trigger associated to it
                .requestRecovery()
                .build();

        return job;
    }

    private JobDataMap createJobDataMap(String jobName, JobsData data) {

        JobDataMap dataMap = new JobDataMap();
        dataMap.put(jobName, data);

        return dataMap;
    }

    private void scheduleJob(Trigger triggerToFire, JobDetail jobTodo, JobListener jobListener) {

        try {

            //check first if this job exits in the scheduler
            //unschedule the trigger/and job if necessary/redundant
            logger.debug("about to check if JOB with jobkey exists");
            if (scheduler.checkExists(jobTodo.getKey())) {
                //scheduler.interrupt(jobKey)
                boolean deleted = scheduler.deleteJob(jobTodo.getKey());
                logger.debug("jobKey: " + jobTodo.getKey() + " found and deleted: " + deleted + ", so that it can be rescheduled");
            }

            scheduler.scheduleJob(jobTodo, triggerToFire);
            scheduler.start();
            scheduler.getListenerManager().addJobListener(jobListener);
            logger.debug("trigger successfuly added");
        } catch (SchedulerException ex) {
            logger.error("error linking job to trigger and starting scheduler: " + ex.getMessage());
        }
    }

    /**
     * Method will remove this trigger from the given Job, but other triggers if
     * any associated if not removed will still fire this job use deleteJob() to
     * completely remove the entire job with all its associated triggers
     *
     * @param trigger returned when the job to be canceled was scheduled
     */
    public void deleteATrigger(Trigger trigger) {

        TriggerKey triggerKey = trigger.getKey();

        //unschedule the trigger/and job if necessary/redundant
        //TriggerKey keyTrigger = triggerKey(String.valueOf(triggerName, Scheduler.DEFAULT_GROUP);
        try {
            boolean unscheduled = scheduler.unscheduleJob(triggerKey);
            logger.debug("triggerName: " + triggerKey.getName() + " - unscheduled : " + unscheduled);
        } catch (SchedulerException ex) {
            logger.error("error trying to unschedule trigger: " + ex.getMessage());
        }
    }

    /**
     * Method will remove this trigger from the given Job, but other triggers if
     * any associated if not removed will still fire this job use deleteJob() to
     * completely remove the entire job with all its associated triggers
     *
     * @param triggerName of the trigger to be removed from the job
     */
    public void deleteATrigger(String triggerName) {

        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName);

        //TriggerKey triggerKey = trigger.getKey();
        //unschedule the trigger/and job if necessary/redundant
        //TriggerKey keyTrigger = triggerKey(String.valueOf(triggerName, Scheduler.DEFAULT_GROUP);
        try {
            boolean unscheduled = scheduler.unscheduleJob(triggerKey);
            logger.debug("triggerName: " + triggerKey.getName() + " - unscheduled : " + unscheduled);
        } catch (SchedulerException ex) {
            logger.error("error trying to unschedule trigger: " + ex.getMessage());
        }
    }

    /**
     * Destroy all sheduled jobs, timers, triggers, etc as well as shutdown the
     * sharedscheduler Call this method when shutting down the
     * application/daemon
     *
     * @throws SchedulerException
     */
    public void cancelAllJobs() throws SchedulerException {
        customSharedScheduler.destroyScheduler();
    }
}
