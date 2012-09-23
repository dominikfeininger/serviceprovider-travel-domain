package places

import groovyx.net.http.*
import groovy.xml.*
import grails.converters.XML
import org.codehaus.groovy.grails.web.xml.*
import places.PlaceHelper

/*
 GOOGLE

 CULTURE PLACES
 art_gallery|cemetery|church|hindu_temple|library|museum|rv_park|stadium|synagogue|zoo
 //match
 Monument,Museum,Church,Nature,Landmark,
 and
 art_gallery|cemetery|church|hindu_temple|library|museum|rv_park|stadium|synagogue|zoo
 */

class CulturePlaceController {

	def index() {
		render(text: "culturePlcae index")
	}
	
	def getEventsForMuseum(){
		render(PlaceHelper.getServerCode210XML() as XML, contentType:"text/xml")
	}

	def findInMinRange(){
		try{
			def range = PlaceHelper.calcRangeForDuration(params.minRange)
			if(range != null){
				//System.out.println("range : " + range);
				redirect(action:"findInKmRange", params:[myLat:"$params.myLat", myLng:"$params.myLng", radius:"$range", kind:"$params.kind"])
			}else{
				//Parameter Error
				render(PlaceHelper.getServerCode251XML() as XML, contentType:"text/xml")
				return
			}
		}catch(Exception){

			render(PlaceHelper.getServerCode261XML() as XML, contentType:"text/xml")
		}
	}

	def findInDuration(){
		try{
			if(params.duration != null){
				if(Integer.parseInt(params.duration) > 120){
					//System.out.println("over 120")
					render(PlaceHelper.getServerCode100XML() as XML, contentType:"text/xml")
				}else{
					//System.out.println("under 120")
					redirect(action:"findInKmRange", params:[myLat:"$params.myLat", myLng:"$params.myLng", radius:"2000	", kind:"$params.kind"])
					//System.out.println("over 120")
				}
			}else{
				//Parameter Error
				render(PlaceHelper.getServerCode251XML() as XML, contentType:"text/xml")
				return
			}
		}catch(Exception){
			render(PlaceHelper.getServerCode261XML() as XML, contentType:"text/xml")
		}
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
			def kind = PlaceHelper.convertCulturePlaceForGoogle(params.kind)
			if((myLatitude != null) && (myLongitude != null) && (range != null) && (kind != null)){
				//System.out.println("myLatitude: " + myLatitude)
				//System.out.println("myLongitude: " + myLongitude)

				String uRL = "https://maps.googleapis.com/maps/api/place/search/xml?location=$myLatitude,$myLongitude&radius=$range&types=$kind&sensor=true&key=AIzaSyBr9DXHMIE0FENaFKFE7P_S7HSmXh9-9Io"
				//System.out.println("uRL: " + uRL);
				//request
				def result = PlaceHelper.makeHTTPRequestWithXML(uRL)
				def dataR = result.toString()

				render( dataR.result.type as XML, contentType:"text/xml")
			}else{
				//Parameter Error
				render( PlaceHelper.getServerCode251XML() as XML, contentType:"text/xml")
				return
			}
		}catch(Exception){
			render(PlaceHelper.getServerCode261XML() as XML, contentType:"text/xml")
		}
	}
}
