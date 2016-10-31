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

import Modelos.Tiposervicio;
import Servicios.TipoServicioS;


@Controller
@RequestMapping("/tiposervicio/*")
public class tiposervicio {
	
	@Autowired
	TipoServicioS tiposervicioS;
	
	
	@RequestMapping("gestion")
	public String gestion(){
		return "tiposervicio/gestion";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("lista")
	public @ResponseBody Map<?, ?> lista(HttpServletRequest request, Integer draw, Integer start, boolean estado,int length)throws IOException{
		Map<String, Object> Data = new HashMap<String, Object>();
		String search = request.getParameter("search[value]");
		List<?> lista=tiposervicioS.listar(start, estado, search,length);
		String total=lista==null||lista.size()==0?"0":((Map<String, Object>) lista.get(0)).get("Tot").toString();	
		Data.put("draw", draw);
		Data.put("recordsTotal", total);
		Data.put("data", lista);
		Data.put("recordsFiltered", !search.equals("")?lista.size():total);
		return Data;

	}
	@RequestMapping("guardar")
	public @ResponseBody Map<String, Object> guardar(Tiposervicio s)throws IOException{
		Map<String, Object> Data = new HashMap<String, Object>();
		Data.put("status", tiposervicioS.adicionar(s));
		return Data;		
	}
	@RequestMapping("actualizar")
	public @ResponseBody Map<String, Object> actualizar(Tiposervicio s)throws IOException{
		Map<String, Object> Data = new HashMap<String, Object>();
		Data.put("status", tiposervicioS.modificar(s));
		return Data;		
	}
	@RequestMapping("eliminar")
	public @ResponseBody Map<String, Object> eliminar(int cod_tipser)throws IOException{
		Map<String, Object> Data = new HashMap<String, Object>();
		Data.put("status", tiposervicioS.darestado(cod_tipser,false));
		return Data;
	}
	@RequestMapping("activar")
	public @ResponseBody Map<String, Object> activar(int cod_tipser)throws IOException{
		Map<String, Object> Data = new HashMap<String, Object>();
		Data.put("status", tiposervicioS.darestado(cod_tipser,true));
		return Data;
	}
	@RequestMapping("validar")
	public @ResponseBody Map<String, Object> validarCi(String nom_tipser){
		Map<String, Object> Data = new HashMap<String, Object>();
		Data.put("status", tiposervicioS.validarNom(nom_tipser));
		return Data;
	}
	@RequestMapping("obtener")
	public @ResponseBody Map<String, Object> obtener(int cod_tipser){
		Map<String, Object> Data = new HashMap<String, Object>();
		try {
			Data.put("data", tiposervicioS.obtener(cod_tipser));
			Data.put("status", true);
		} catch (Exception e) {
			Data.put("status", false);
			System.out.println("error al obtener="+e.toString());
		}
		return Data;
	}

}