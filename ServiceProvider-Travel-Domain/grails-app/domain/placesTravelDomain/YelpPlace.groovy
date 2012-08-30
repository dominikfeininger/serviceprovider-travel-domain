package placesTravelDomain

class YelpPlace {

    static constraints = {
    }
	
	def scaffold = true

	String name
	//String coordinate
	String latitude
	String longitude
	String reviews
	String text_excerpt 
	String photo_url
	
	YelpPlace(){
		
	}
	
	YelpPlace(String tmpName, String tmpCoordinate){
		this.name = tmpCenter
		this.coordinate = tmpCoordinate
	}
	
	YelpPlace(String tmpName, String tmpLatitude, tmpLongitude, String tmpSnippet_Text, String tmpImage_Url){
		this.name = tmpCenter
		//this.coordinate = tmpCoordinate
		this.latitude = tmpLatitude
		this.longitude = tmpLongitude
		this.text_excerpt = tmpSnippet_Text
		this.photo_url = tmpImage_Url
	}
	
	
}
