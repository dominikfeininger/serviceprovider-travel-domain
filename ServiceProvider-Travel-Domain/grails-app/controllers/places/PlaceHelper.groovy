package places;

import com.sun.jdi.IntegerValue;
import groovyx.net.http.*
import grails.converters.JSON
import static groovyx.net.http.ContentType.*

public class PlaceHelper {

	public static String serverCode251JSON = "{\"server_code\":\"251\", \"message\":\"Parmeter Error in URL\"}"
	public static String serverCode100JSON = "{\"server_code\":\"100\", \"message\":\"Success\"}"
	public static String serverCode261JSON = "{\"server_code\":\"261\", \"message\":\"Connection Error\"}"
	public static String serverCode210JSON = "{\"server_code\":\"210\", \"message\":\"Success, but no Results\"}"

	public static String serverCode251XML = "<?xml version=\"1.0\"?><node><server_code>251</server_code><message>Parmeter Error in URL</message></node>"
	public static String serverCode100XML = "<?xml version=\"1.0\"?><node><server_code>100</server_code><message>Success</message></node>"
	public static String serverCode261XML = "<?xml version=\"1.0\"?><node><server_code>261</server_code><message>Connection Error</message></node>"
	public static String serverCode210XML = "<?xml version=\"1.0\"?><node><server_code>210</server_code><message>Success, but no Results</message></node>"

	static String getServerCode251XML(){
		return serverCode251XML
	}

	static String getServerCode100XML(){
		return serverCode100XML
	}

	static String getServerCode261XML(){
		return serverCode261XML
	}

	static String getServerCode210XML(){
		return serverCode210XML
	}

	static String getServerCode251JSON(){
		return serverCode251JSON
	}

	static String getServerCode100JSON(){
		return serverCode100JSON
	}

	static String getServerCode261JSON(){
		return serverCode261JSON
	}

	static String getServerCode210JSON(){
		return serverCode210JSON
	}
	static String convertCulturePlaceForGoogle(String kind){

		if(kind == "Monument")
			kind = "hindu_temple%7Crv_park%7Csynagogue"
		else if(kind == "Museum")
			kind = "museum%7Cart_gallery"
		else if(kind == "Church")
			kind = "church%7Chindu_temple%7Ccemetery%7Csynagogue"
		else if(kind == "Nature")
			kind = "rv_park"
		else if(kind == "Landmark")
			kind = "zoo%7Cstadium"

		return kind
	}

	static String calcRangeForDuration(String tmpDuration){
		//adapt min range in meters divided by 1000 to get km * 20min (user needs 20min per km)
		int duration = Integer.parseInt(tmpDuration)
		return (duration/20)*1000
	}

	static String makeHTTPRequestWithJson(String uRL){
		//build HttpURLClient object
		def http = new HttpURLClient( )
		//request
		def resp = http.request(url: uRL)
		//make it JSON format
		return resp.getData().toString()
	}

	static String makeHTTPRequestWithXML(String uRL){
		//build HttpURLClient object
		def http = new HttpURLClient( )
		//request
		def resp = http.request(url:uRL)
		//System.out.println("resp: " + resp.data);
		//return it
		return resp.getData()
	}
}
