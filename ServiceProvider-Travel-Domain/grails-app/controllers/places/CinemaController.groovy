package places

class CinemaController {

    def index() { 
		render(text: "cinema index")
	}
	
	def findInMinRange(){
		render(text: "findInMinRange")
	}
	
	def findInDuration(){
		render(text: "findInDuration")
	}
	
	def findInKmRange(){
		render(text: "findInKmRange")
	}
}
