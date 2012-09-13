package places

import groovy.json.*
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.*
import placesTravelDomain.GooglePlace;
import groovyx.net.http.*
import places.PlaceHelper

/*
 GOOGLE
 RESTAURANT
 bar
 cafe
 food
 meal_delivery
 meal_takeaway
 */

class RestaurantController {

	def index(){
		render(text:"restaurant index")
	}

	def findInKmRange(){
		try{
			//parse url
			def myLatitude = params.myLat
			def myLongitude = params.myLng
			def range = params.radius
			if(params.radius == "0"){
				range = "2000"
			}
			def cuisine = params.cuisine
			//System.out.println("myLatitude: " + myLatitude)
			//System.out.println("myLongitude: " + myLongitude)

			//make request
			String uRL = "https://maps.googleapis.com/maps/api/place/search/json?location=$myLatitude,$myLongitude&radius=$range&types=food&keyword=$cuisine&sensor=false&key=AIzaSyBr9DXHMIE0FENaFKFE7P_S7HSmXh9-9Io"
			//System.out.println("uRL: " + uRL);
			def result = PlaceHelper.makeHTTPRequestWithJson(uRL)
			//def dataR = result.toString()
			//def jsonObj = new JsonSlurper().parseText(dataR)
			//def results = jsonObj.results
			System.out.println("result: " + result);
			/*
			 GooglePlace place
			 def gPlaces =[]
			 JSONArray inputArray = new JSONArray(jsonObj.results);
			 inputArray.each {entry ->
			 JSONObject jo = entry
			 place = new GooglePlace(jo.reference.toString(),jo.name.toString(),jo.geometry.toString(),jo.types.toString(),jo.vicinity.toString(),jo.icon.toString(), jo.lat.toString(), jo,lng.toString())
			 //System.out.println("place.name :" + place.name);
			 //System.out.println("place.geometry :" + place.geometry);
			 //System.out.println("place.types :" + place.types);
			 //System.out.println("place.vicinity :" + place.vicinity);
			 gPlaces.add(place)
			 }
			 */
			def madeData = result.substring(1)
			def serverCode = "{\"server_code\":\"100\", \"range\":\"$range\","
			//System.out.println("place.name :" + place.name);
			render(text:serverCode + madeData)//(gPlaces as JSON) + "}")//, contentType : 'text/json')
		}catch(Exception){
			render(text:PlaceHelper.getServerCode251JSON())
		}
	}

	def findInMinRange(){
		try{
			def range = PlaceHelper.calcRangeForDuration(params.minRange)
			//System.out.println("range : " + range);
			redirect(action:"findInKmRange", params:[myLat:"$params.myLat", myLng:"$params.myLng", radius:"$range", cuisine:"$params.cuisine"])
		}catch(Exception){

			render(text: PlaceHelper.getServerCode251JSON())
		}
	}

	def findInDuration(){
		try{
			if(Integer.parseInt(params.duration) > 120){
				System.out.println("over 120")
				render(text: PlaceHelper.getServerCode100JSON())
			}else{
				System.out.println("under 120")
				redirect(action:"findInKmRange", params:[myLat:"$params.myLat", myLng:"$params.myLng", radius:"2000	", cuisine:"$params.cuisine"])
			}
		}catch(Exception){
			render(text: PlaceHelper.getServerCode251JSON())
		}
	}
}