package Controladores;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import Modelos.Modelo;
import Servicios.ModeloS;


@Controller
@RequestMapping("/modelo/*")
public class modelo {
	
	@Autowired
	ModeloS modeloS;
	
	
	@RequestMapping("gestion")
	public String gestion(){
		return "modelo/gestion";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("lista")
	public @ResponseBody Map<?, ?> lista(HttpServletRequest request, Integer draw, Integer start, boolean estado,int length)throws IOException{
		Map<String, Object> Data = new HashMap<String, Object>();
		String search = request.getParameter("search[value]");
		List<?> lista=modeloS.listar(start, estado, search,length);
		String total=lista==null||lista.size()==0?"0":((Map<String, Object>) lista.get(0)).get("Tot").toString();	
		Data.put("draw", draw);
		Data.put("recordsTotal", total);
		Data.put("data", lista);
		Data.put("recordsFiltered", !search.equals("")?lista.size():total);
		return Data;

	}
	@RequestMapping("guardar")
	public @ResponseBody Map<String, Object> guardar(Modelo m)throws IOException{
		Map<String, Object> Data = new HashMap<String, Object>();
		Data.put("status", modeloS.adicionar(m));
		return Data;		
	}
	@RequestMapping("actualizar")
	public @ResponseBody Map<String, Object> actualizar(Modelo m)throws IOException{
		Map<String, Object> Data = new HashMap<String, Object>();
		Data.put("status", modeloS.modificar(m));
		return Data;		
	}
	@RequestMapping("eliminar")
	public @ResponseBody Map<String, Object> eliminar(int cod_mod)throws IOException{
		Map<String, Object> Data = new HashMap<String, Object>();
		Data.put("status", modeloS.darestado(cod_mod,false));
		return Data;
	}
	@RequestMapping("activar")
	public @ResponseBody Map<String, Object> activar(int cod_mod)throws IOException{
		Map<String, Object> Data = new HashMap<String, Object>();
		Data.put("status", modeloS.darestado(cod_mod,true));
		return Data;
	}
	@RequestMapping("validar")
	public @ResponseBody Map<String, Object> validarCi(String nom_mod){
		Map<String, Object> Data = new HashMap<String, Object>();
		Data.put("status", modeloS.validarNom(nom_mod));
		return Data;
	}
	@RequestMapping("obtener")
	public @ResponseBody Map<String, Object> obtener(int cod_mod){
		Map<String, Object> Data = new HashMap<String, Object>();
		try {
			Data.put("data", modeloS.obtener(cod_mod));
			Data.put("status", true);
		} catch (Exception e) {
			Data.put("status", false);
			System.out.println("error al obtener="+e.toString());
		}
		return Data;
	}

}