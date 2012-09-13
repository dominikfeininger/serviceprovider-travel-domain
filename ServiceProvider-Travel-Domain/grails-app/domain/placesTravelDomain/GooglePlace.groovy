package placesTravelDomain

class GooglePlace {

	def scaffold = true
	
	static mapping = {
		autoImport false
	}
	
	//String id
	String geometry
	String icon
	String name
	
	//is unique id
	String reference
	String vicinity
	String types
	
	String lng
	String lat
	//String server_code
	//String html_attributions
	//String results

	GooglePlace(){
	}

	static constraints = {
	}

	GooglePlace(String tmpName){
		this.name = tmpName
	}
	
	GooglePlace(String tmpReference, String tmpName, String tmpGeometry, String tmpTypes, String tmpVicinity, String tmpIcon){
		this.reference = tmpReference
		this.icon = tmpIcon
		this.name = tmpName
		this.vicinity = tmpVicinity
		this.geometry = tmpGeometry
		this.types = tmpTypes
	}

	GooglePlace(String tmpReference, String tmpName, String tmpGeometry, String tmpTypes, String tmpVicinity, String tmpIcon, String tmpLat, String tmpLng){
		this.lat = tmpLat
		this.lng = tmpLng
		this.reference = tmpReference
		this.icon = tmpIcon
		this.name = tmpName
		this.vicinity = tmpVicinity
		this.geometry = tmpGeometry
		this.types = tmpTypes
	}

}
