package placesTravelDomain

class YelpPlace {

    static constraints = {
    }
	
	def scaffold = true

	String name
	String tmp
	
	YelpPlace(){
		
	}
	
	YelpPlace(String tmpName, String tmpS){
		this.name = tmpName
		this.tmp = tmpS
	}
}
