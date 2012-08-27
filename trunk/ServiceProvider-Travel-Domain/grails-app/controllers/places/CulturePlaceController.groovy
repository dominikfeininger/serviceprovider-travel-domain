package places

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
		render(text: "findInKmRange")
	}
}
