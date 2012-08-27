package places

import groovy.json.*
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.*

import placesTravelDomain.GooglePlace;
import groovyx.net.http.*
/*import static groovyx.net.http.ContentType.JSON
 import static groovyx.net.http.Method.POST
 import groovyx.net.http.HTTPBuilder*/

class RestaurantController {

	def index(){
		render(text:"restaurant index")
	}

	def findInKmRange(){

		//parse url
		def myLatitude = params.myLat
		def myLongitude = params.myLon
		def range = params.radius
		def cuisine = params.cuisine
		//System.out.println("myLatitude: " + myLatitude)
		//System.out.println("myLongitude: " + myLongitude)

		//make request
		def http = new HttpURLClient()
		//setup url, returns jason, makes request with google api places key:AIzaSyBr9DXHMIE0FENaFKFE7P_S7HSmXh9-9Io
		String staticUrl = "https://maps.googleapis.com/maps/api/place/search/json?location=$myLatitude,$myLongitude&radius=$range&types=food&keyword=$cuisine&sensor=false&key=AIzaSyBr9DXHMIE0FENaFKFE7P_S7HSmXh9-9Io"
		//System.out.println("staticUrl: " + staticUrl);
		//request
		def result = http.request(url: staticUrl)
		def dataR = result.getData().toString()
		def madeData = dataR.substring(1)
		def serverCode = "{\"server_code\":\"100\","
		def data = serverCode += madeData += "}"
		def jsonObj = new JsonSlurper().parseText(data)

		def results = jsonObj.results
		//def JSONresults = results as JSON
		//System.out.println("results: " + JSONresults);

		GooglePlace place// = new GooglePlace()//jsonObj.name)
		//GooglePlace place = new GooglePlace("Soja Bohnen")
		//System.out.println("place.name :" + place.name);
		//place.save(flush : true)

		JSONArray inputArray = new JSONArray(jsonObj.results);
		inputArray.each {entry ->
			JSONObject jo = entry
			place = new GooglePlace(jo.name.toString(),jo.geometry.toString(),jo.types.toString(),jo.vicinity.toString())
			//System.out.println("place.name :" + place.name);
			//System.out.println("place.geometry :" + place.geometry);
			//System.out.println("place.types :" + place.types);
			//System.out.println("place.vicinity :" + place.vicinity);
			//TODO:save if needed
			//place.save(flush:true)
			/*if (!place.save(flush:true)) {
			 place.errors.each {
			 println it
			 }
			 }*/
		}
		//System.out.println("syso");
		//System.out.println("place.name :" + place.name);
		render(text:place as JSON)//inputArray.length())
	}
	
	def findInMinRange(){
		render(text: "findInMinRange")
	}
	
	def findInDuration(){
		render(text: "findInDuration")
	}
}
