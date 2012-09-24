package places

import groovy.json.*
import groovy.xml.*
import grails.converters.XML
import org.codehaus.groovy.grails.web.xml.*
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.*
import groovyx.net.http.*
import places.PlaceHelper


/*
 GOOGLE
 CINEMA/ MOVIE
 movie_rental
 movie_theater
 moving_company
 */

class CinemaController {

	def index() {
		render(text: "cinema index")
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
			def movieType = params.movieType
			if((myLatitude != null) && (myLongitude != null) && (range != null) && (movieType != null)){
				//System.out.println("myLatitude: " + myLatitude)
				//System.out.println("myLongitude: " + myLongitude)

				String uRL = "https://maps.googleapis.com/maps/api/place/search/json?location=$myLatitude,$myLongitude&radius=$range&types=movie_theater&sensor=false&key=AIzaSyBr9DXHMIE0FENaFKFE7P_S7HSmXh9-9Io"
				//System.out.println("uRL: " + uRL);
				//request
				def result = PlaceHelper.makeHTTPRequestWithJson(uRL)
				def madeData = result.substring(1)

				def serverCode = "{\"server_code\":\"100\", \"range\":\"$range\","
				//System.out.println("place.name :" + place.name);
				render(text:serverCode + madeData)
			}else{
				//Parameter Error
				render(text: PlaceHelper.getServerCode251JSON())
				return
			}
		}catch(Exception){
			render(text:PlaceHelper.getServerCode261JSON())
		}
	}

	def findInMinRange(){
		try{
			def range = PlaceHelper.calcRangeForDuration(params.minRange)
			if(range != null){
				//System.out.println("range : " + range);
				redirect(action:"findInKmRange", params:[myLat:"$params.myLat", myLng:"$params.myLng", radius:"$range", movieType:"$params.movieType"])
			}else{
				//Parameter Error
				render(text: PlaceHelper.getServerCode251JSON())
				return
			}
		}catch(Exception){

			render(text: PlaceHelper.getServerCode261JSON())
		}
	}

	def findInDuration(){
		try{
			if(params.duration != null){
				if(Integer.parseInt(params.duration) > 120){
					//System.out.println("over 120")
					render(text: PlaceHelper.getServerCode100JSON())
				}else{
					//System.out.println("under 120")
					redirect(action:"findInKmRange", params:[myLat:"$params.myLat", myLng:"$params.myLng", radius:"2000	", movieType:"$params.movieType"])
				}
			}else{
				//Parameter Error
				render(text: PlaceHelper.getServerCode251JSON())
				return
			}
		}catch(Exception){
			render(text: PlaceHelper.getServerCode261JSON())
		}
	}
	
	def getMoviesForCiema(){
		render(text: PlaceHelper.getServerCode210XML() as String, contentType:"text/xml", encoding:"UTF-8")
	}

}
