package com.jira.services;

import com.jira.exceptions.ProjectNotFoundException;
import com.jira.models.Backlog;
import com.jira.models.ProjectTask;
import com.jira.repositories.BacklogRepository;
import com.jira.repositories.ProjectRepository;
import com.jira.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
        Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
        projectTask.setBacklog(backlog);
        Integer backlogSequence = backlog.getPTSequence();
        backlogSequence++;
        backlog.setPTSequence(backlogSequence);
        projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        if(projectTask.getStatus() == null || projectTask.getStatus().equals(""))
            projectTask.setStatus("TO_DO");

        if(projectTask.getPriority() == null || projectTask.getPriority() == 0)
            projectTask.setPriority(3);

        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String backlog_id, String username) {
        projectService.findProjectByIdentifier(backlog_id,username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String sequence, String username) {
        projectService.findProjectByIdentifier(backlog_id,username);
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(sequence);
        if(projectTask==null)
            throw new ProjectNotFoundException("Project Task" + sequence + " is not available!!");

        if(!projectTask.getProjectIdentifier().equals(backlog_id))
            throw new ProjectNotFoundException("Project Task " + sequence + " does not exist in the backlog: " + backlog_id);

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updateTask, String backlog_id, String pt_id, String username) {
        findPTByProjectSequence(backlog_id,pt_id,username);
        return projectTaskRepository.save(updateTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id,username);
        projectTaskRepository.delete(projectTask);
    }
}
