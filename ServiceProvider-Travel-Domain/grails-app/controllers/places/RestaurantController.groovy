package places
//@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.2' )

import groovy.json.*
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.*
import serviceprovider.travel.domain.GooglePlace

class RestaurantController {

	def index(){
		render(text:"restaurant index")
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[placeInstanceList: GooglePlace.list(params), placeInstanceList: GooglePlace.count()]
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
		def result = http.request(url: staticUrl)
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
