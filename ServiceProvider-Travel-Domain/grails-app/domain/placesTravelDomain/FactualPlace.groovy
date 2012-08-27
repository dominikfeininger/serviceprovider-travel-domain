package placesTravelDomain

class FactualPlace {

    static constraints = {
		
    }
	
	
	def scaffold = true

	String name
	String tmp
	
	FactualPlace(){
		
	}
	
	FactualPlace(String tmpName, String tmpS){
		this.name = tmpName
		this.tmp = tmpS
	}
	
}
