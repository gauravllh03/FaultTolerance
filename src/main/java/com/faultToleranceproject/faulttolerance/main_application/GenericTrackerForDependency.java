package com.faultToleranceproject.faulttolerance.main_application;
public class GenericTrackerForDependency {
	/*
	 * @param dependencies: array of method numbers on which dependency is there.
	 * @param id: id for which confirmation is needed that it has been processed by all dependencies
	 * already
	 */
	protected static void waitForDependency(int dependencies[],int id)
	{
		for(int dependency:dependencies){
			if(dependency==1){
				while(!Method1.Method1VisibilityMap.containsKey(id));
			}
			else if(dependency==2){
				while(!Method2.Method2VisibilityMap.containsKey(id));
			}
			else if(dependency==3){
				while(!Method3.Method3VisibilityMap.containsKey(id));
			}
			else if(dependency==4){
				while(!Method4.Method4VisibilityMap.containsKey(id));
			}
			else if(dependency==5){
				while(!Method5.Method5VisibilityMap.containsKey(id));
			}
			else{
				while(!Method6.Method6VisibilityMap.containsKey(id));
			}
		}
	}
}


