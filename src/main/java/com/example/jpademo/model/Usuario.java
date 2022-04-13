
package com.example.jpademo.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Usuarios")

public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Auto_incremente MYSQL
	private Integer id;
	private String userName;
	private String nombre;
	private String email;
	private String password;
	private Integer estatus;
	private Date fechaRegistro;
	
	//Un usuario va a tener muchos perfiles
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable( name="UsuarioPerfil",
			    joinColumns = @JoinColumn (name="idUsuario"),
			    inverseJoinColumns = @JoinColumn(name="idPerfil")
			
			)
	
	private List<Perfil> perfiles; //La variable es de typo list porque regresa varios
	
	public void agregar(Perfil tempPerfil) {
		if(perfiles == null) {
			 perfiles = new LinkedList<Perfil>();
		}
		perfiles.add(tempPerfil);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	public Integer getEstatus() {
		return estatus;
	}
	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	
	
	public List<Perfil> getPerfiles() {
		return perfiles;
	}
	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", userName=" + userName + ", nombre=" + nombre + ", email=" + email
				+ ", password=" + password + ", estatus=" + estatus + ", fechaRegistro=" + fechaRegistro + "]";
	}
	
	
}
