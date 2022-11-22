package safeseattle.model;

import java.sql.Timestamp;

public class Posts {
	protected int postId;
	protected Users user;
	protected Reports report;
	protected String title;
	protected String content;
	protected Timestamp created;
	

// This constructor can be used for reading records from MySQL, where we have all fields,
// including the PostId.
public Posts(int postId, Users user, Reports report, String title, String content, Timestamp created) {
		super();
		this.postId = postId;
		this.user = user;
		this.report = report;
		this.title = title;
		this.content = content;
		this.created = created;
	}

//This constructor can be used for reading records from MySQL, where we only have the postId,
	// such as a foreign key reference to PostId.
	// Given PostId, we can fetch the full BlogPost record.
	public Posts(int postId) {
		this.postId = postId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Reports getReport() {
		return report;
	}

	public void setReportId(Reports report) {
		this.report = report;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}
	
	
	
	
}