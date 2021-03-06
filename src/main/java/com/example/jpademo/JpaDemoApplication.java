package com.example.jpademo;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.jpademo.model.Categoria;
import com.example.jpademo.model.Perfil;
import com.example.jpademo.model.Usuario;
import com.example.jpademo.model.Vacante;
import com.example.jpademo.repository.CategoriasRepository;
import com.example.jpademo.repository.PerfilesRepository;
import com.example.jpademo.repository.UsuariosRepository;
import com.example.jpademo.repository.VacantesRepository;

@SpringBootApplication
public class JpaDemoApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriasRepository repoCategorias;	
	
	@Autowired
	private VacantesRepository repoVacantes;
	
	@Autowired
	private PerfilesRepository repoPerfiles;
	
	@Autowired
	private UsuariosRepository repoUsuarios;	
	
		
	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);
	}	

	@Override
	public void run(String... args) throws Exception {
		//guardar();
		//buscarPorId();0
		//eliminar();
		//conteo();
		//eliminarTodos();
		//buscarTodos();
		//existeId();
		//guardarTodas();
		//*****
		//buscarTodosJpa();
		//borrarTodoenBloque();
		//buscarTodosOrdenados();
		//buscarTodosOrdenadosPorNombre();
		//buscarTodosPaginacion();
		//buscarTodosPaginacionOrdenados();
		//buscarVacantes();
		//guardarVacante();
		//crearPerfilesAplicacion();
		//crearUsuarioConDosPerfiles();
		//buscarUsuario();
		//buscarVacantesPorEstatus();
		//buscarVacantesPorDestacadoYEstatus();
		//buscarVacantesSalario();
		buscarVacantesVariosEstatus();
	}
	private void buscarVacantesVariosEstatus() {
		String[] estatus = new String[] {"Eliminada", "Creada"};
		List<Vacante> lista = repoVacantes.findByEstatusIn(estatus);
		System.out.println("Registros encontrados: " + lista.size());
		for(Vacante v: lista) {
			System.out.println(v.getId() + ": " + v.getNombre()+ ": " + v.getEstatus());
		}
	}
	
	
	private void buscarVacantesSalario() {
		List<Vacante> lista = repoVacantes.findBySalarioBetweenOrderBySalarioDesc(7000, 14000);
		System.out.println("Registros encontrados: " + lista.size());
		for(Vacante v: lista) {
			System.out.println(v.getId() + ": " + v.getNombre()+ ": $" + v.getSalario());
		}
	}
	
	private void buscarVacantesPorDestacadoYEstatus() {
		List<Vacante> lista = repoVacantes.findByDestacadoAndEstatusOrderByIdDesc(1,"Aprobada");
		System.out.println("Registros encontrados: " + lista.size());
		for(Vacante v: lista) {
			System.out.println(v.getId() + ": " + v.getNombre()+ ": " + v.getEstatus() + v.getDestacado());
		}
	}
	
	
	private void buscarVacantesPorEstatus() {
		List<Vacante> lista = repoVacantes.findByEstatus("Aprobada");
		System.out.println("Registros encontrados: " + lista.size());
		for(Vacante v: lista) {
			System.out.println(v.getId() + ": " + v.getNombre()+ ": " + v.getEstatus());
		}
	}
	
	public void buscarUsuario() {
		Optional<Usuario> optional = repoUsuarios.findById(1);
		if(optional.isPresent()) {
			Usuario u = optional.get();
			System.out.println("Usuario: "+ u.getNombre());
			System.out.println("Perfiles asignados");
			for(Perfil p : u.getPerfiles()) {
				System.out.println(p.getPerfil());
			}
		}else {
			System.out.println("Usuario no encontrado");
		}
	}
	
	/**
	 * Crear un usuario con dos perfiles ("ADMINISTRADOR", "USUARIO")
	 */
	
	private void crearUsuarioConDosPerfiles() {
		Usuario user = new Usuario();
		user.setNombre("Negro negrolo");
		user.setEmail("negro.gmail.com");
		user.setFechaRegistro(new Date());
		user.setUserName("Negrolin");
		user.setPassword("12345");
		user.setEstatus(1);
		
		Perfil per1 = new Perfil();
		per1.setId(2);
		
		Perfil per2 = new Perfil();
		per2.setId(3);
		
		user.agregar(per1);
		user.agregar(per2);
		
		repoUsuarios.save(user);
		
	}
	
	private void crearPerfilesAplicacion() {
		repoPerfiles.saveAll(getPerfilesAplicacion());
	}
	
	private void buscarTodosPaginacionOrdenados() {
		Page<Categoria> page = repoCategorias.findAll(PageRequest.of(1, 5,Sort.by("nombre").descending()));
		System.out.println("Total Registros: " + page.getTotalElements());
		System.out.println("Total Registros: " + page.getTotalPages());
		for(Categoria c: page.getContent()) {
			System.out.println(c.getId() + " " + c.getNombre());
		}
	}	
	
	
	/**
	 * Este metodo divide las tuplas de las bases en un numero determinado, que es lo
	 * que llamamos paginacion,y luego podemos llamar a los lotes con la instruccion 
	 * Page<Categoria> page = repo.findAll(PageRequest.of(1, 5)); el 1 hace referencia a la 
	 * segunda pagina y el 5 hace referencia al numero de registros por pagina
	 */	
	private void buscarTodosPaginacion() {
		Page<Categoria> page = repoCategorias.findAll(PageRequest.of(1, 5));
		System.out.println("Total Registros: " + page.getTotalElements());
		System.out.println("Total Registros: " + page.getTotalPages());
		for(Categoria c: page.getContent()) {
			System.out.println(c.getId() + " " + c.getNombre());
		}
	}	
	
	/**
	 * Metodo findAll(Ordenados por un campo) - Interfaz PaginAndSortingRepository
	 * En este caso se selecciono el nombre
	 */
	
	private void buscarTodosOrdenadosPorNombre() {
		List<Categoria> categorias = repoCategorias.findAll(Sort.by("nombre").descending());
		for(Categoria c : categorias) {
			System.out.println(c.getId() + " " + c.getNombre());
		}
	}
	
	
	/**
	 * Metodo findAll(Ordenados por un campo) - Interfaz PaginAndSortingRepository
	 * Por defecto los ordena por el id
	 */
	private void buscarTodosOrdenados() {
		List<Categoria> categorias = repoCategorias.findAll();
		for(Categoria c : categorias) {
			System.out.println(c.getId() + " " + c.getNombre());
		}
	}
	
	/**
	 * Metodo deleleteAllInBatch (Usar con precaucion) - Intefaz JpaRepository
	 */
	private void borrarTodoenBloque() {
		repoCategorias.deleteAllInBatch();
	}
	
	
	/**
	 * Metodo findAll - Interfaz JpaRepository
	 */
	
	private void buscarVacantes() {
		List<Vacante> lista = repoVacantes.findAll();
		for(Vacante v : lista) {
			System.out.println(v.getId() + " " + v.getNombre() + "-> " + v.getCategoria().getNombre());
		}
	}
	
	private void guardarVacante() {
		Vacante vacante = new Vacante();
		vacante.setNombre("Profesor de Matematica");
		vacante.setDescripcion("Escuela primaria solicita profesor para curso de Matematicas");
		vacante.setFecha(new Date());
		vacante.setSalario(8500.0);
		vacante.setEstatus("Aprobada");
		vacante.setDestacado(0);
		vacante.setImagen("escuela.png");
		vacante.setDetalles("<h1>Los requisitos para profesor de Matematicas </h1>");
		Categoria cat = new Categoria();
		cat.setId(15);
		vacante.setCategoria(cat);
		repoVacantes.save(vacante);
	}
	
	
	private void buscarTodosJpa() {
		List<Categoria> categorias = repoCategorias.findAll();
		for(Categoria c : categorias) {
			System.out.println(c.getId() + " " + c.getNombre());
		}
	}
	
	private void guardarTodas() {
		List<Categoria> categorias = getListaCategorias();
		repoCategorias.saveAll(categorias);
	}
	
	
	
	/**
	 * Metodo existById - Busca por Id en la tabla 
	 */	
	private void existeId() {
		
		boolean existe = repoCategorias.existsById(50);
		System.out.println("La categoria existe: " + existe);
	}	
	
	private void buscarTodos() {
		
		Iterable<Categoria> categorias =repoCategorias.findAll();
		for(Categoria cat : categorias) {
			System.out.println(cat);
		}				
	}
	
	
	/**
	 * Metodo findAllById - Interfaz CrudRepository
	 */
	private void encontrarPorIds() {
		List<Integer> ids =new LinkedList<Integer>(); 
		
		ids.add(1);
		ids.add(4);
		ids.add(10);
		
		Iterable<Categoria> categorias  = repoCategorias.findAllById(ids);
		for(Categoria cat: categorias) {
			System.out.println(cat);
		}
	}
	
	
	private void eliminarTodos() {
		repoCategorias.deleteAll();
	}
	
	private void conteo() {
		long count = repoCategorias.count();
		System.out.println("Total Categorias: " + count);
	}
	
	public void eliminar() {
		int idCategoria = 3;
		repoCategorias.deleteById(idCategoria);
		
	}
	
	public void modificar() {
		Optional<Categoria> optional = repoCategorias.findById(2);
		if(optional.isPresent()) {
			
			Categoria catTmp = optional.get();
			catTmp.setNombre("Ing. de desarrollo");
			catTmp.setDescripcion("Desarrollo de sistemas");
			
			repoCategorias.save(catTmp);
		}	else {
				System.out.println("Categoria no encontrada");
			}
		
	}	
	
	public void buscarPorId() {
		Optional<Categoria> optional = repoCategorias.findById(5);
		if(optional.isPresent()) {
			System.out.println(optional.get());
		}	else {
				System.out.println("Categoria no encontrada");
			}		
	}	
	
	private void guardar() {
		Categoria cat = new Categoria();
		cat.setNombre("Finanzas");
		cat.setDescripcion("Trabajos relacionados con finanzas y contabilidad");  
		repoCategorias.save(cat);
		System.out.println(cat);
	}
	
	private List<Categoria> getListaCategorias(){
		
		List<Categoria> lista = new LinkedList<Categoria>();
		//Categoria 1
		Categoria cat1 = new Categoria();
		cat1.setNombre("Programador de Blockchain");
		cat1.setDescripcion("Trabajos relacionados con Bitcoin y Criptomonedas");
		
		//Categoria 2
		Categoria cat2 = new Categoria();
		cat2.setNombre("Soldador/Pintura");
		cat2.setDescripcion("Trabajos relacionados con soldadura, pintura y enderezado");
		
		//Categoria 2
		Categoria cat3 = new Categoria();
		cat3.setNombre("Ingeniero industrial");
		cat3.setDescripcion("Trabajos relacionados con Ingenieria Industrial ");
		
		lista.add(cat1);
		lista.add(cat2);
		lista.add(cat3);
		return lista;
		
	}
	
	/**
	 * Metodo que regresa una lista de objetos de tipo Perfil que representa los diferentes
	 * perfiles o roles que tendremos en nuestra aplicacion de empleos
	 * @return
	 */
	private List<Perfil> getPerfilesAplicacion(){
		List<Perfil> lista = new LinkedList<Perfil>();
		Perfil per1 = new Perfil();
		per1.setPerfil("SUPERVISOR");
		
		Perfil per2 = new Perfil();
		per2.setPerfil("ADMINISTRADOR");
		
		Perfil per3 = new Perfil();
		per3.setPerfil("USUARIO");
		
		lista.add(per1);
		lista.add(per2);
		lista.add(per3);
		
		return lista;
		
	}
	
	

}
