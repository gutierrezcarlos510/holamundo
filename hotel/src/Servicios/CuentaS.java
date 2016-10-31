package Servicios;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import Modelos.Cuenta;
import Utiles.Db_Coneccion;
import Utiles.Letra;
@Service
public class CuentaS extends Db_Coneccion{
		
	public List<Map<String, Object>> listar(int start,boolean estado,String search,int length){
		if(search==null)search="";
		try{
			return db.queryForList("select * from cuenta_lista(?,?,?,?)"+as_object_add(as_cuenta, "RN bigint,Tot int"),start,length,search,estado);
		}catch(Exception e){
			System.out.println("error listarCuenta"+e.toString());
			return null;
		}
	}
	public Map<String, Object> obtener(int cod_cue){
		try {
			return db.queryForMap("select * from cuenta_obtener(?)",cod_cue);
		} catch (Exception e) {
			System.out.println("error obtenerCuenta"+e.toString());
			return null;
		}
	}
	public boolean adicionar (Cuenta c){
		try {
			Letra l=new Letra();
			return db.queryForObject("select cuenta_adicionar(?,?,?)", Boolean.class,l.Primera_Mayuscula(c.getNom_cue()),c.getDes_cue(),c.isTip_cue());
		} catch (Exception e) {
			System.out.println("error adicionarCuenta"+e.toString());
			return false;
		}
	}
	public boolean modificar (Cuenta c){
		try {
			Letra l=new Letra();
			return db.queryForObject("select cuenta_modificar(?,?,?,?)", Boolean.class,l.Primera_Mayuscula(c.getNom_cue()),c.getDes_cue(),c.isTip_cue(),c.getCod_cue());
		} catch (Exception e) {
			System.out.println("error modificarCuenta"+e.toString());
			return false;
		}
	}
	public boolean darestado (int cod_cue,boolean est_cue){
		try {
			return db.queryForObject("select cuenta_darestado(?,?)", Boolean.class,cod_cue,est_cue);
		} catch (Exception e) {
			System.out.println("error darestadoCuenta"+e.toString());
			return false;
		}
	}
	public boolean validarNom (String nom){
		return db.queryForObject("select cuenta_validar", Boolean.class,nom);
	}
}
