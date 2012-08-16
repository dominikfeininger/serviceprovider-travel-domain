package places

import groovy.json.*

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.*
import serviceprovider.travel.domain.GooglePlace
//import groovyx.net.http.*
import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.POST
import groovyx.net.http.HTTPBuilder

class RestaurantController {

	def index(){
		render(text:"restaurant index")
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[placeInstanceList: GooglePlace.list(params), placeInstanceList: GooglePlace.count()]
	}   
	
	def findAtYelp(){
		def http = new HTTPBuilder("http://api.yelp.com/business_review_search?term=yelp&tl_lat=37.9&tl_long=-122.5&br_lat=37.788022&br_long=-122.399797&limit=3&ywsid=xmchYhnGZKTKWDn4ZfxlZA")
		http.request(POST, JSON) {  
			//uri.path = ""  
			//uri.query = [param1: 'p1']  
			response.success = { resp, json ->    
				System.out.println("success")  
			}  
		} 
		render(text:response as JSON)
	} 
	
	def findAtGoogle(){
		
		//parse url
		def myLatitude = params.mylat
		def myLongitude = params.mylon
		def range = params.range
		def cuisine = params.cuisine
		//System.out.println(latitude)
		
		//make request
		//def http = new HttpURLClient()
		//setup url, returns jason, makes request with google api places key:AIzaSyBr9DXHMIE0FENaFKFE7P_S7HSmXh9-9Io
		String staticUrl = "https://maps.googleapis.com/maps/api/place/search/json?location=$myLatitude,$myLongitude&radius=$range&types=food&keyword=$cuisine&sensor=false&key=AIzaSyBr9DXHMIE0FENaFKFE7P_S7HSmXh9-9Io"
		//request
		def result = "{\"server_code\":100}"//= http.request(url: staticUrl)
		def data = result.getData().toString()
		def jsonObj = new JsonSlurper().parseText(data)

		GooglePlace place1 = new GooglePlace("googleplacename")
		place1.types = "googleplacetypes"
		place1.save()
		
		//System.out.println(GooglePlace.get() as JSON)
		
		GooglePlace place
		JSONArray inputArray = new JSONArray(jsonObj.results);
		inputArray.each {entry ->
			JSONObject jo = entry
			place = new GooglePlace(jo.name.toString(),jo.geometry.toString(),jo.types.toString(),jo.vicinity.toString())
			/*
			if (!place.save()) {
				place.errors.each {
					println it
				}
			}*/
		}
		render(text:place as JSON)//inputArray.length())
	}
}
