package uni.fmi.masters.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "username", nullable = false, unique = true, length = 20)
	private String username;
	
	@Column(name = "email", length = 60)
	private String email;
	
	@Column(name = "password", nullable = false, length = 40)
	private String password;
	
	@Column(name = "age")
	private int age;

	@Column(name = "avatar", length = 255)
	private String avatar;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<CommentBean> comments;

	public UserBean() {
		// TODO Auto-generated constructor stub
	}
	
	public UserBean(String username, String email, String password) {
		this.email = email;
		this.password = password;
		this.username = username;		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public List<CommentBean> getComments() {
		return comments;
	}

	public void setComments(List<CommentBean> comments) {
		this.comments = comments;
	}

}
