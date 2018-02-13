<div class="generic-container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Destination search </span></div>
		<div class="panel-body">
	        <div class="formcontainer">
	            <div class="alert alert-success" role="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
	            <div class="alert alert-danger" role="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>

                <div class="table-responsive">
                    <h4>Search by Categories</h4>
                    <div class="btn-group">
                        <label class="btn btn-primary" ng-model="tripCategoryModel.OUTDOOR_ACTIVITIES" uib-btn-checkbox>Outdoor Activities</label>
                        <label class="btn btn-primary" ng-model="tripCategoryModel.HIKING" uib-btn-checkbox>Hiking</label>
                        <label class="btn btn-primary" ng-model="tripCategoryModel.CYCLING" uib-btn-checkbox>Cycling</label>
                        <label class="btn btn-primary" ng-model="tripCategoryModel.MUSIC_CONCERTS" uib-btn-checkbox>Music concerts</label>
                        <label class="btn btn-primary" ng-model="tripCategoryModel.THEATRE" uib-btn-checkbox>Theatre</label>
                        <label class="btn btn-primary" ng-model="tripCategoryModel.MUSEUMS" uib-btn-checkbox>Museums</label>
                        <label class="btn btn-primary" ng-model="tripCategoryModel.ARCHITECTURE" uib-btn-checkbox>Architecture</label>
                        <label class="btn btn-primary" ng-model="tripCategoryModel.RESTAURANTS" uib-btn-checkbox>Restaurants</label>
                        <label class="btn btn-primary" ng-model="tripCategoryModel.SHOPPING" uib-btn-checkbox>Shopping</label>
                        <label class="btn btn-primary" ng-model="tripCategoryModel.BEACHES" uib-btn-checkbox>Beaches</label>
                        <label class="btn btn-primary" ng-model="tripCategoryModel.NIGHT_LIFE" uib-btn-checkbox>Night Life</label>
                        <label class="btn btn-primary" ng-model="tripCategoryModel.KIDS_FAMILY_ACTIVITIES" uib-btn-checkbox>Kids/Family Activities</label>
                        <label class="btn btn-primary" ng-model="tripCategoryModel.WALK" uib-btn-checkbox>Walk</label>
                        <label class="btn btn-primary" ng-model="tripCategoryModel.SPORT" uib-btn-checkbox>Sport</label>
                    </div>
                    <h4>Search by Holiday Duration</h4>
                    <div class="btn-group">
                        <label class="btn btn-success" ng-model="holidayLengthModel" uib-btn-radio="'ONE_DAY'">One Day</label>
                        <label class="btn btn-success" ng-model="holidayLengthModel" uib-btn-radio="'WEEKEND'">Weekend</label>
                        <label class="btn btn-success" ng-model="holidayLengthModel" uib-btn-radio="'LONG_STAY'">Long Stay</label>
                    </div>
                </div>
    	    </div>
		</div>	
    </div>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Destinations </span></div>
		<div class="panel-body">
			<div class="table-responsive">
		        <table class="table table-hover">
		            <thead>
		            <tr>
		                <th>NAME</th>
		                <th>TOP CATEGORIES</th>
		            </tr>
		            </thead>
		            <tbody>
		            <tr ng-repeat="d in ctrl.getAllDestinations()">
		                <td>{{d.name}}</td>
		                <td>{{d.topCategories}}</td>
		            </tr>
		            </tbody>
		        </table>		
			</div>
		</div>
    </div>
</div>