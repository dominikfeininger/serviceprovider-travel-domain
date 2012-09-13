package navigation

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.*
import placesTravelDomain.GooglePlace;
import groovyx.net.http.*
import groovy.xml.*
import grails.converters.XML
import org.codehaus.groovy.grails.web.xml.*
import places.PlaceHelper

class NavigationController {

	def index() {
		render(text: "navigation index")
	}

	def getRoute(){
		try{
			//parse url
			def myLatitude = params.mylat
			def myLongitude = params.mylng
			def destLatitude = params.destlat
			def destLongitude = params.destlng
			//System.out.println(latitude)

			//make request
			String uRL = "http://maps.google.com/maps?saddr=$myLatitude,$myLongitude&daddr=$destLatitude,$destLongitude&output=html&dirflg=w&output=dragdir"
			def result = PlaceHelper.makeHTTPRequestWithJson(uRL)
			//System.out.println("uRL:" + uRL);
			//System.out.println("result" + result);
			//removes the first character from the result
			def madeData = result.substring(1)
			//adds server code
			def tmpServerCode = "{\"server_code\":\"100\","
			//combines everything
			def concat = tmpServerCode += madeData
			//make it JSON format
			def jsonRep = JSON.parse(concat)
			System.out.println(concat)
			render (text:concat)//contentType: "text/json", text: jsonRep as JSON )
		}catch(Exception){
			render(text: PlaceHelper.getServerCode251JSON())
		}
	}
}
