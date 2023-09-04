package com.example.hrm_new.entity.employee;

public class UpdateResponse {
	
	private String message;
    private ProjectWork projectWork;

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ProjectWork getProjectWork() {
		return projectWork;
	}

	public void setProjectWork(ProjectWork projectWork) {
		this.projectWork = projectWork;
	}

	public UpdateResponse(String message, ProjectWork projectWork) {
        this.message = message;
        this.projectWork = projectWork;
    }

}
