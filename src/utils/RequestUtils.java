package utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.eclipse.persistence.internal.helper.SimpleDatabaseType;

public class RequestUtils {

	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
	
	/**
	 * 
	 * @param request
	 * @param nombreParametro
	 * @return
	 * @throws FormularioIncorrectoRecibidoException
	 */
	public static int getIntParameter (HttpServletRequest request, String nombreParametro) throws FormularioIncorrectoRecibidoException {
		try {
			return Integer.parseInt(getStringParameter(request, nombreParametro));
		} catch (Exception e) {
			throw new FormularioIncorrectoRecibidoException("No se ha recibido valor entero para el par�metro " + nombreParametro);
		}
	}
	
	public static float getFloatParameter (HttpServletRequest request, String nombreParametro) throws FormularioIncorrectoRecibidoException {
		try {
			return Float.parseFloat(getStringParameter(request, nombreParametro));
		} catch (Exception e) {
			throw new FormularioIncorrectoRecibidoException("No se ha recibido valor flotante para el par�metro " + nombreParametro);
		}
	}
	
	public static long getDateParameter (HttpServletRequest request, String nombreParametro) throws FormularioIncorrectoRecibidoException {
		try {
			return Date.parse(getStringParameter(request, nombreParametro));
		} catch (Exception e) {
			throw new FormularioIncorrectoRecibidoException("No se ha recibido la fecha para el par�metro " + nombreParametro);
		}
	}

	/**
	 * 
	 * @param request
	 * @param nombreParametro
	 * @return
	 * @throws FormularioIncorrectoRecibidoException
	 */
	public static String getStringParameter (HttpServletRequest request, String nombreParametro)  {
		return request.getParameter(nombreParametro);
	}

	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static HashMap<String, Object> requestToHashMap (HttpServletRequest request) {
		HashMap<String, Object> hashMap = new HashMap<String, Object> ();
		try {
			// Debo diferenciar el caso de tener una request de tipo multipart a una de tipo normal
			if (ServletFileUpload.isMultipartContent(request)) { // Caso de que el request sea de tipo multipart
				FileItemFactory factory = new DiskFileItemFactory();
			    ServletFileUpload upload = new ServletFileUpload(factory);
			    Iterator<FileItem> items = upload.parseRequest(request).iterator();
			    // Una vez obtenidos todos los campos del formulario, busco uno de ellos, que no sea de tipo Fichero y cuyo nombre coincida con el nombre buscado
			    while (items.hasNext()) {
			        FileItem thisItem = (FileItem) items.next();
			        if (thisItem.isFormField()) {
			        	hashMap.put(thisItem.getFieldName(), thisItem.getString());
			        }
			        else {
			        	// En el caso de que recibamos un fichero tenemos m�todos para tener el nombre del fichero, su tipo, tama�o, etc. pero aqu� nos conformaremos
			        	// s�lo con obtener el fichero como un array de bytes
			        	hashMap.put(thisItem.getFieldName(), thisItem.get());
			        }

			    }
			} 
			// Si son request normales se aplica lo siguiente
			else {
				Enumeration<String> enNombresDeParametros = request.getParameterNames();
				while (enNombresDeParametros.hasMoreElements()) {
					String nombreDeParametro = enNombresDeParametros.nextElement();
					hashMap.put(nombreDeParametro, request.getParameter(nombreDeParametro));
				}		
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hashMap;
	}


	/**
	 * 
	 * @param request
	 * @param nombreParametro
	 * @return
	 */
	public static String getStringParameterFromHashMap (HashMap<String, Object> hashMap, String nombreParametro)  {
		return (String) hashMap.get(nombreParametro);
	}

	/**
	 * 
	 * @param request
	 * @param nombreParametro
	 * @return
	 */
	public static int getIntParameterFromHashMap (HashMap<String, Object> hashMap, String nombreParametro)  {
		try {
			return Integer.parseInt((String) hashMap.get(nombreParametro));
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static float getFloatParameterFromHashMap (HashMap<String, Object> hashMap, String nombreParametro)  {
		try {
			return Float.parseFloat((String) hashMap.get(nombreParametro));
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static long getDateParameterFromHashMap (HashMap<String, Object> hashMap, String nombreParametro)  {
		try {
			return Date.parse((String) hashMap.get(nombreParametro));
		} catch (Exception e) {
			return 0;
		}
	}

	
	/**
	 * 
	 * @param hashMap
	 * @param nombreParametro
	 * @return
	 */
	public static byte[] getByteArrayFromHashMap (HashMap<String, Object> hashMap, String nombreParametro)  {
		try {
			return (byte[]) hashMap.get(nombreParametro);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param hashMap
	 * @param nombreParametro
	 * @return
	 * @throws FormularioIncorrectoRecibidoException
	 */
	public static String getMandatoryStringParameterFromHashMap (HashMap<String, Object> hashMap, String nombreParametro) throws FormularioIncorrectoRecibidoException  {
		if (getStringParameterFromHashMap(hashMap, nombreParametro) != null) {
			return getStringParameterFromHashMap(hashMap, nombreParametro);
		}
		else {
			throw new FormularioIncorrectoRecibidoException("No se ha recibido valor para el par�metro " + nombreParametro);
		}
	}
	
	/**
	 * 
	 * @param hashMap
	 * @param nombreParametro
	 * @return
	 * @throws FormularioIncorrectoRecibidoException
	 */
	public static int getMandatoryIntParameterFromHashMap (HashMap<String, Object> hashMap, String nombreParametro) throws FormularioIncorrectoRecibidoException  {
		try {
			return Integer.parseInt((String) getMandatoryStringParameterFromHashMap(hashMap, nombreParametro));
		} catch (Exception e) {
			throw new FormularioIncorrectoRecibidoException("No se ha recibido valor entero para el par�metro " + nombreParametro);
		}
	}

}
