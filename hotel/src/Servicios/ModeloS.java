package Servicios;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import Modelos.Modelo;
import Utiles.Db_Coneccion;
import Utiles.Letra;
@Service
public class ModeloS extends Db_Coneccion{

	
	public List<Map<String, Object>> listar(int start,boolean estado,String search,int length){
		if(search==null)search="";
		try{
			return db.queryForList("select * from modelo_lista(?,?,?,?)"+as_object_add(as_modelo, "RN bigint,Tot int"),start,length,search,estado);
		}catch(Exception e){
			System.out.println("error listarModelo"+e.toString());
			return null;
		}
	}
	public Map<String, Object> obtener(int cod_mod){
		try {
			return db.queryForMap("select * from modelo_obtener(?)"+as_modelo,cod_mod);
		} catch (Exception e) {
			System.out.println("error obtenerModelo"+e.toString());
			return null;
		}
	}
	public boolean adicionar (Modelo m){
		try {
			Letra l=new Letra();
			return db.queryForObject("select modelo_adicionar(?,?,?)", Boolean.class,l.Primera_Mayuscula(m.getNom_mod()),m.getPre_mod(),m.getPredes_mod());
		} catch (Exception e) {
			System.out.println("error adicionarModelo"+e.toString());
			return false;
		}
	}
	public boolean modificar (Modelo m){
		try {
			Letra l=new Letra();
			return db.queryForObject("select modelo_modificar(?,?,?,?)", Boolean.class,l.Primera_Mayuscula(m.getNom_mod()),m.getPre_mod(),m.getPredes_mod(),m.getCod_mod());
		} catch (Exception e) {
			System.out.println("error modificarModelo"+e.toString());
			return false;
		}
	}
	public boolean darestado (int cod_mod,boolean est_mod){
		try {
			return db.queryForObject("select modelo_darestado(?,?)", Boolean.class,cod_mod,est_mod);
		} catch (Exception e) {
			System.out.println("error darestadoModelo"+e.toString());
			return false;
		}
	}
	public boolean validarNom (String nom){
		return db.queryForObject("select modelo_validar", Boolean.class,nom);
	}
}
