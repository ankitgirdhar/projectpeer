package com.jira.services;

import com.jira.exceptions.ProjectIdException;
import com.jira.exceptions.ProjectNotFoundException;
import com.jira.models.Backlog;
import com.jira.models.Project;
import com.jira.models.UserModel;
import com.jira.repositories.BacklogRepository;
import com.jira.repositories.ProjectRepository;
import com.jira.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project, String username) {
        String identifier = project.getProjectIdentifier().toUpperCase();

        if(project.getId()!=null) {
            Project existingProject = projectRepository.findByProjectIdentifier(identifier);

            if(existingProject == null)
                throw new ProjectNotFoundException("Project with id: " + identifier + " does not exist and thus cannot be modified!");

            if(!existingProject.getProjectLeader().equals(username))
                throw new ProjectNotFoundException("Project not found in your account!!");
        }

        try {
            UserModel user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(identifier);

            if(project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(identifier);
            } else {
                project.setBacklog(backlogRepository.findByProjectIdentifier(identifier));
            }
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID: " + identifier + " already exists!");
        }
    }

    public Project findProjectByIdentifier(String projectId , String username) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null)
            throw new ProjectIdException("Project with ID: " + projectId + " does not exists!");

        if(!project.getProjectLeader().equals(username))
            throw new ProjectNotFoundException("Project is not found in the account!!");

        return project;
    }

    public Iterable<Project> findAllProjects(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username) {
        projectRepository.delete(findProjectByIdentifier(projectId,username));
    }


}
