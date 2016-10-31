package Servicios;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import Modelos.Tipohabitacion;
import Utiles.Db_Coneccion;
import Utiles.Letra;
@Service
public class TipoHabitacionS extends Db_Coneccion{
	
	public List<Map<String, Object>> listar(int start,boolean estado,String search,int length){
		if(search==null)search="";
		try{
			return db.queryForList("select * from tipohabitacion_lista(?,?,?,?)"+as_object_add(as_tipohabitacion, "RN bigint,Tot int"),start,length,search,estado);
		}catch(Exception e){
			System.out.println("error listarTipohabitacion"+e.toString());
			return null;
		}
	}
	public Map<String, Object> obtener(int cod_tiphab){
		try {
			return db.queryForMap("select * from tipohabitacion_obtener(?)"+as_tipohabitacion,cod_tiphab);
		} catch (Exception e) {
			System.out.println("error obtenerTipohabitacion"+e.toString());
			return null;
		}
	}
	public boolean adicionar (Tipohabitacion th){
		try {
			Letra l=new Letra();
			return db.queryForObject("select tipohabitacion_adicionar(?,?)", Boolean.class,l.Primera_Mayuscula(th.getNom_tiphab()),th.getDes_tiphab());
		} catch (Exception e) {
			System.out.println("error adicionarTipohabitacion"+e.toString());
			return false;
		}
	}
	public boolean modificar (Tipohabitacion th){
		try {
			Letra l=new Letra();
			return db.queryForObject("select tipohabitacion_modificar(?,?,?)", Boolean.class,l.Primera_Mayuscula(th.getNom_tiphab()),th.getDes_tiphab(),th.getCod_tiphab());
		} catch (Exception e) {
			System.out.println("error modificarTipohabitacion"+e.toString());
			return false;
		}
	}
	public boolean darestado (int cod_tiphab,boolean est_tiphab){
		try {
			return db.queryForObject("select tipohabitacion_darestado(?,?)", Boolean.class,cod_tiphab,est_tiphab);
		} catch (Exception e) {
			System.out.println("error darestadoTipohabitacion"+e.toString());
			return false;
		}
	}
	public boolean validarNom (String nom){
		return db.queryForObject("select tipohabitacion_validar", Boolean.class,nom);
	}
}
