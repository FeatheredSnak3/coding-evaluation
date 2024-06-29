package com.aa.act.interview.org;

import java.util.Optional;
import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;


public abstract class Organization {

    private Position root;
    
    public Organization() {
        root = createOrganization();
    }
    
    protected abstract Position createOrganization();
    
    /**
     * hire the given person as an employee in the position that has that title
     * 
     * @param person
     * @param title
     * @return the newly filled position or empty if no position has that title
     */
    
     //START of my code
     private AtomicInteger nextEmployeeId = new AtomicInteger(1); //Initialize unique ID at 1

    public Optional<Position> hire(Name person, String title) {
        Queue<Position> queue = new LinkedList<>();

        queue.add(root); //Start from Root Position
        
        while (!queue.isEmpty()) {
            Position currPosition = queue.remove(); //Remove head of Queue 
            
            //Determine if current title matches title parameter
            if(currPosition.getTitle() == title) {
                Employee newEmployee = new Employee(createUniqueId(), person);//New Employee for current person 

                currPosition.setEmployee(Optional.of(newEmployee));//Assign Employee to the position
                
                return Optional.of(currPosition); //Return matching position
            }
            
            //Iterate over each Position object within DirectReports
            for (Position directReport : currPosition.getDirectReports()) { 
                queue.add(directReport); //Add direct reports to the end of the queue
            }  
        }

        return Optional.empty();
    }

    private int createUniqueId() {
        return nextEmployeeId.getAndIncrement();
    }//END of my code


    @Override
    public String toString() {
        return printOrganization(root, "");
    }
    
    private String printOrganization(Position pos, String prefix) {
        StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
        for(Position p : pos.getDirectReports()) {
            sb.append(printOrganization(p, prefix + "  "));
        }
        return sb.toString();
    }
}
