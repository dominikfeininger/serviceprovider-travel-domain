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
		render(text: PlaceHelper.getServerCode210XML() as String, contentType:"text/xml", encoding:"UTF-8")
	}

	def findInMinRange(){
		try{
			def range = PlaceHelper.calcRangeForDuration(params.minRange)
			if(range != null){
				//System.out.println("range : " + range);
				redirect(action:"findInKmRange", params:[myLat:"$params.myLat", myLng:"$params.myLng", radius:"$range", kind:"$params.kind"])
			}else{
				//Parameter Error
				render(text: PlaceHelper.getServerCode251XML() as String, contentType:"text/xml", encoding:"UTF-8")
				return
			}
		}catch(Exception){

			render(text: PlaceHelper.getServerCode261XML() as String, contentType:"text/xml", encoding:"UTF-8")
		}
	}

	def findInDuration(){
		try{
			if(params.duration != null){
				if(Integer.parseInt(params.duration) > 120){
					//System.out.println("over 120")
					render(text: PlaceHelper.getServerCode100XML() as String, contentType:"text/xml", encoding:"UTF-8")
				}else{
					//System.out.println("under 120")
					redirect(action:"findInKmRange", params:[myLat:"$params.myLat", myLng:"$params.myLng", radius:"2000	", kind:"$params.kind"])
					//System.out.println("over 120")
				}
			}else{
				//Parameter Error
				render(text: PlaceHelper.getServerCode251XML() as String, contentType:"text/xml", encoding:"UTF-8")
				return
			}
		}catch(Exception){
			render(text:PlaceHelper.getServerCode261XML() as String, contentType:"text/xml", encoding:"UTF-8")
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
			//System.out.println("kind" + kind);
			System.out.println("params.myLat: " + params.myLat)
			System.out.println("myLongitude: " + myLongitude)
			//http://feininger.dyndns.info:8090/ServiceProvider-Travel-Domain/culturePlace/findInKmRange?
			//System.out.println("range: " + range);
			if((myLatitude != null) && (myLongitude != null) && (kind != null) && (range != null)){
				//System.out.println("myLatitude: " + myLatitude)
				//System.out.println("myLongitude: " + myLongitude)

				String uRL = "https://maps.googleapis.com/maps/api/place/search/xml?location=$myLatitude,$myLongitude&radius=$range&types=$kind&sensor=true&key=AIzaSyBr9DXHMIE0FENaFKFE7P_S7HSmXh9-9Io"
				System.out.println("uRL: " + uRL);
				//request
				def result = PlaceHelper.makeHTTPRequestWithXML(uRL)
				def dataR = result.toString()
				System.out.println("dataR: " + dataR);
				render(text: dataR.result.types as String, contentType:"text/xml", encoding:"UTF-8")
			}else{
				//Parameter Error
				System.out.println("parameter error");
				render(text: PlaceHelper.getServerCode251XML() as String, contentType:"text/xml", encoding:"UTF-8")
				return
			}
		}catch(Exception){
			render(text: PlaceHelper.getServerCode261XML() as String, contentType:"text/xml", encoding:"UTF-8")
		}
	}
}
