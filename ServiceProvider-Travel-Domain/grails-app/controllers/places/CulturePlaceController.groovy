package places

import groovy.json.*
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.*
import placesTravelDomain.GooglePlace;
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

	def findInMinRange(){
		try{
			render(text: PlaceHelper.getServerCode100JSON()())
		}catch(Exception){

			render(text: PlaceHelper.getServerCode251JSON())
		}
	}

	def findInDuration(){
		try{
			render(text: PlaceHelper.getServerCode100JSON()())
		}catch(Exception){

			render(text: PlaceHelper.getServerCode251JSON())
		}
	}

	def findInKmRange(){
		try{
			//parse url
			def myLatitude = params.myLat
			def myLongitude = params.myLon
			def range = params.radius
			if(params.radius == "0"){
				//TODO: change
				range = "2000"
			}
			def kind = PlaceHelper.convertCulturePlaceForGoogle(params.kind)
			//System.out.println("myLatitude: " + myLatitude)
			//System.out.println("myLongitude: " + myLongitude)

			String uRL = "https://maps.googleapis.com/maps/api/place/search/xml?location=$myLatitude,$myLongitude&radius=$range&types=$kind&sensor=true&key=AIzaSyBr9DXHMIE0FENaFKFE7P_S7HSmXh9-9Io"
			//System.out.println("uRL: " + uRL);
			//request
			def result = PlaceHelper.makeHTTPRequestWithJson(uRL)
			def dataR = result.getData()//.toString()
			/*
			 System.out.println("result: " + result);
			 System.out.println("dataR: " + dataR);
			 System.out.println("dataR.status: " + dataR.status)
			 System.out.println("dataR.result.name: " + dataR.result.name)
			 System.out.println("\n\nXML response: ${dataR.status}");
			 System.out.println("dataR.status: " + dataR.status);
			 */
			render(text: dataR.result.type)
		}catch(Exception){
			render(text:PlaceHelper.getServerCode251JSON())
		}
	}
}
