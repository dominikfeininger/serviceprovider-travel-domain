package places;

import groovyx.net.http.*
import grails.converters.JSON

public class PlaceHelper {

	public static String serverCode151JSON = "\"server_code\":\"151\", \"message\":\"service not available\"}"
	public static String serverCode100JSON = "\"server_code\":\"100\", \"message\":\"success\"}"
	public static String serverCode251JSON = "\"server_code\":\"251\", \"message\":\"failed\"}"

	public static String serverCode151XML = "<?xml version=\"1.0\"?><node><server_code>151</server_code><messgae>service not available</messgae></node>"
	public static String serverCode100XML = "<?xml version=\"1.0\"?><node><server_code>100</server_code><messgae>success</messgae></node>"
	public static String serverCode251XML = "<?xml version=\"1.0\"?><node><server_code>251</server_code><messgae>failed</messgae></node>"

	static String getServerCode151XML(){
		return serverCode151XML
	}

	static String getServerCode100XML(){
		return serverCode100XML
	}

	static String getServerCode251XML(){
		return serverCode251XML
	}

	static String getServerCode151JSON(){
		return serverCode151JSON
	}

	static String getServerCode100JSON(){
		return serverCode100JSON
	}

	static String getServerCode251JSON(){
		return serverCode251JSON
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


	static String makeHTTPRequestWithJson(String uRL){
		//build HttpURLClient object
		def http = new HttpURLClient( )
		//request
		def resp = http.request(url: uRL)
		//make it JSON format
		return JSON.parse(resp.getData().toString())
	}

	static String makeHTTPRequestWithXML(String uRL){
		//build HttpURLClient object
		def http = new HttpURLClient( )
		//request
		def resp = http.request(url: uRL)
		//return it
		//TODO: impl right way
		return resp.getData().toString()
	}
}
