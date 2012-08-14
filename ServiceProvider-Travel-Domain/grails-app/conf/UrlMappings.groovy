class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')
		
		//general mobile app API
		//"/ServiceProviders/$controller/$action?/$id?" { }
		//"/ServiceProviders/$controller/$action" { }
	}
}
