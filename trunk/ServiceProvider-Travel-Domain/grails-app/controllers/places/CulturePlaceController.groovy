package places

//json
import groovy.json.*
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.*

import placesTravelDomain.GooglePlace;

//http
import groovyx.net.http.*

//xml
import groovy.xml.*
import grails.converters.XML
import org.codehaus.groovy.grails.web.xml.*

/*
GOOGLE
CULTURE PLACES

art_gallery|cemetery|church|hindu_temple|library|museum|rv_park|stadium|synagogue|zoo


*/



class CulturePlaceController {

    def index() { 	
		render(text: "culturePlcae index")
	}
	
	def findInMinRange(){
		render(text: "findInMinRange")
	}
	
	def findInDuration(){
		render(text: "findInDuration")
	}
	
	def findInKmRange(){
		
		//parse url
		def myLatitude = params.myLat
		def myLongitude = params.myLon
		def range = params.radius
		if(params.radius == "0"){
			//TODO: change
			range = "2000"
		}
		def kind = params.kind
		//System.out.println("myLatitude: " + myLatitude)
		//System.out.println("myLongitude: " + myLongitude)

		//match
		/*
		Monument,Museum,Church,Nature,Landmark,
		and
		art_gallery|cemetery|church|hindu_temple|library|museum|rv_park|stadium|synagogue|zoo
		*/
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
			
		//make request
		def http = new HttpURLClient()
		//setup url, returns jason, makes request with google api places key:AIzaSyBr9DXHMIE0FENaFKFE7P_S7HSmXh9-9Io
		String staticUrl = "https://maps.googleapis.com/maps/api/place/search/xml?location=$myLatitude,$myLongitude&radius=$range&types=$kind&sensor=true&key=AIzaSyBr9DXHMIE0FENaFKFE7P_S7HSmXh9-9Io"
		System.out.println("staticUrl: " + staticUrl);
		//request
		def result = http.request(url: staticUrl)
		def dataR = result.getData()//.toString()
	
		System.out.println("result: " + result);
		System.out.println("dataR: " + dataR);
		
		System.out.println("dataR.status: " + dataR.status)
		System.out.println("dataR.result.name: " + dataR.result.name)
		
		System.out.println("\n\nXML response: ${dataR.status}");
		
		System.out.println("dataR.status: " + dataR.status);
		render(text: dataR.result.type)
	}
}
