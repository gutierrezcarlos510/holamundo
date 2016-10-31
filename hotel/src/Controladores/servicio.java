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

import Modelos.Servicio;
import Servicios.ServicioS;


@Controller
@RequestMapping("/servicio/*")
public class servicio {
	
	@Autowired
	ServicioS servicioS;
	
	
	@RequestMapping("gestion")
	public String gestion(){
		return "menu/gestion";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("lista")
	public @ResponseBody Map<?, ?> lista(HttpServletRequest request, Integer draw, Integer start, boolean estado,int length)throws IOException{
		Map<String, Object> Data = new HashMap<String, Object>();
		String search = request.getParameter("search[value]");
		List<?> lista=servicioS.listar(start, estado, search,length);
		String total=lista==null||lista.size()==0?"0":((Map<String, Object>) lista.get(0)).get("Tot").toString();	
		Data.put("draw", draw);
		Data.put("recordsTotal", total);
		Data.put("data", lista);
		Data.put("recordsFiltered", !search.equals("")?lista.size():total);
		return Data;

	}
	@RequestMapping("guardar")
	public @ResponseBody Map<String, Object> guardar(Servicio s)throws IOException{
		Map<String, Object> Data = new HashMap<String, Object>();
		Data.put("status", servicioS.adicionar(s));
		return Data;		
	}
	@RequestMapping("actualizar")
	public @ResponseBody Map<String, Object> actualizar(Servicio s)throws IOException{
		Map<String, Object> Data = new HashMap<String, Object>();
		Data.put("status", servicioS.modificar(s));
		return Data;		
	}
	@RequestMapping("eliminar")
	public @ResponseBody Map<String, Object> eliminar(int cod_ser)throws IOException{
		Map<String, Object> Data = new HashMap<String, Object>();
		Data.put("status", servicioS.darestado(cod_ser,false));
		return Data;
	}
	@RequestMapping("activar")
	public @ResponseBody Map<String, Object> activar(int cod_ser)throws IOException{
		Map<String, Object> Data = new HashMap<String, Object>();
		Data.put("status", servicioS.darestado(cod_ser,true));
		return Data;
	}
	@RequestMapping("validar")
	public @ResponseBody Map<String, Object> validarCi(String nom_ser){
		Map<String, Object> Data = new HashMap<String, Object>();
		Data.put("status", servicioS.validarNom(nom_ser));
		return Data;
	}
	@RequestMapping("obtener")
	public @ResponseBody Map<String, Object> obtener(int cod_ser){
		Map<String, Object> Data = new HashMap<String, Object>();
		try {
			Data.put("data", servicioS.obtener(cod_ser));
			Data.put("status", true);
		} catch (Exception e) {
			Data.put("status", false);
			System.out.println("error al obtener="+e.toString());
		}
		return Data;
	}

}