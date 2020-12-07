package fr.enedis.consumerkafka;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "user")
public class User implements Serializable {

	@Id
	private Integer idUser;
	private String name;
	private String lastname;

	public User() {
	}

	public User(Integer idUser, String name, String lastname) {
		this.idUser = idUser;
		this.name = name;
		this.lastname = lastname;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String toString() {
		return "User{" +
			 "idUser=" + idUser +
			 ", name='" + name + '\'' +
			 ", lastname='" + lastname + '\'' +
			 '}';
	}
}
