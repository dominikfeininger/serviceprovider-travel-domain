package serviceprovider.travel.domain

class GooglePlace {
	
	def scaffold = true
	
	String geometry
	String icon
	String name
	String reference
	String vicinity
	String types
	String server_code
	String html_attributions
	String results
	
	GooglePlace(){
		
	}
	
	static constraints = {
		
	}
	
	GooglePlace(String tmpName){
		this.name = tmpName
	}
	
	GooglePlace(String tmpName, String tmpGeometry, String tmpTypes, String tmpVicinity){
		this.name = tmpName
		this.vicinity = tmpVicinity
		this.geometry = tmpGeometry
		this.types = tmpTypes
	}
	
	// list everything
}
