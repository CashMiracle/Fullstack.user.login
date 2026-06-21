package spring_boot_react_auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="User")
public class User {
	@Id
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String passwordHash;
	private LocalDateTime createdAt;

	public User() {}

	public User(String firstName, String lastName, String email, String passwordHash) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	public String getId() {return id;}
	public String getFirstName() {return firstName;}
	public void setFirstName(String firstName) {this.firstName = firstName;}
	public String getLastName() {return lastName;}
	public void setLastName(String lastName) {this.lastName = lastName;}
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	public String setPassword(String password){passwordHash = endcoder.encode(password);}	
	public boolean verifyPassword(String passwordHash){return this.passwordHash == passwordHash;}
	public String toString(){
		return "User [id=" + id + ", Name=" + firstName + " " + lastName + ", Email=" + email;
	}
}
