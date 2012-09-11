package navigation

class NavigationController {

	def index() {
		render(text: "navigation index")
	}
	
	def getRoute(){
		try{
			render(text: PlaceHelper.getServerCode100JSON()())
		}catch(Exception){

			render(text: PlaceHelper.getServerCode251JSON())
		}
	}
}
